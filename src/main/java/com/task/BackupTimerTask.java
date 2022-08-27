package com.task;

import com.alibaba.druid.util.StringUtils;
import com.common.vo.WindowsCmdResult;
import com.pojo.*;
import com.service.*;
import com.utils.Base64Util;
import com.utils.FileUtil;
import com.utils.TimeFormatUtil;
import com.utils.WindowsCmdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Fang Ruichuan
 * @version 1.0
 * @description: 定时备份数据库任务以及定时清理过期数据库的备份文件
 * @date 2021/10/29 19:13
 */
@Component
public class BackupTimerTask {

    private final static Logger log = LoggerFactory.getLogger(BackupTimerTask.class);

    @Resource(name = "backupRecordServiceImpl")
    private BackupRecordService backupRecordService;

    @Resource(name = "tableOfDatabaseServiceImpl")
    private TableOfDatabaseService tableOfDatabaseService;

    @Resource(name = "databaseConfigServiceImpl")
    private DatabaseConfigService databaseConfigService;

    @Resource(name = "ruleServiceImpl")
    private RuleService ruleService;

    @Resource(name = "clearLogServiceImpl")
    private ClearLogService clearLogService;

    @Resource(name = "backupFileServiceImpl")
    private BackupFileService backupFileService;

    @Resource(name = "jobExeDetailServiceImpl")
    private JobExeDetailService jobExeDetailService;

    @Value("${linux.root}")
    private String linuxRoot;

    // 每隔1分钟扫描数据库配置进行备份任务
    @Async
    @Scheduled(cron = "0 */1 * * * ?")
    public void backupTimer() {
        log.info("当前时间：{}, 数据库备份任务线程称为：{}", LocalDateTime.now(), Thread.currentThread().getName());
        Calendar cal = Calendar.getInstance();
        Integer week = cal.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Date backupDate = new Date();
        String specialTime = sdf.format(backupDate);
        log.info("执行时间：{}", TimeFormatUtil.dateToDirectoryName(backupDate));


        List<DatabaseConfig> tasks = databaseConfigService.findAllTasks();

        // 用来存储定时任务状态为开启的任务ID
        int[] taskIdArr = new int[Math.toIntExact(databaseConfigService.getConnectionInfoTotal())];

        int index = 0;
        for (DatabaseConfig task : tasks) {
            // 如果任务的定时任务状态为开启，则添加定时备份任务中
            if (Byte.valueOf((byte) 1).equals(task.getExecFlag())) {
                taskIdArr[index++] = task.getId();
            }
        }

        log.info("taskIdArr => {}", Arrays.toString(taskIdArr));

        for (int taskId : taskIdArr) {
            List<Rule> rules = ruleService.getRule(taskId);
            for (Rule rule : rules) {

                String backupWholePoint = rule.getBackupWholePoint();
                String[] split = backupWholePoint.split(",");

                for (String backupTime : split) {

                    if (week.equals(rule.getBackupWeek()) && specialTime.equals(backupTime)) {
                        DatabaseConfig task = databaseConfigService.select(rule.getDatabaseId());

                        new Thread(() -> {
                            log.info("今天是 {}, {}点整, 开始备份 {}数据库", intToWeekString(rule.getBackupWeek()), backupTime, task.getDatabaseName());
                            // 执行备份动作
                            backup(task, backupDate);
                        }, "备份线程[" + task.getDatabaseName() + "]").start();
                    }
                }
            }
        }
    }

    private String intToWeekString(int w) {
        String[] weeks = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weeks[w-1];
    }

    // 执行备份动作
    private synchronized void backup(DatabaseConfig task, Date backupDate) {
        long start = System.currentTimeMillis();
        // 获取备份信息，执行备份任务
        Integer id = task.getId();
        String hostIp = task.getHostIp();
        Integer hostPort = task.getHostPort();
        String username = task.getUsername();
        String password = Base64Util.decode(task.getPassword());
        String databaseName = task.getDatabaseName();
        String savePath = task.getSavePath();
        String fileName = task.getFileName();
        String saveType = task.getSaveType();
        Integer dbVersion = task.getDbVersion();

        // 获取执行文件名
        DatabaseConfig select = databaseConfigService.select(id);
        String exeFileName = select.getDbversionConfig().getExeFileName();
        log.info("执行文件名：{}", exeFileName);


        // 如果路径中不以/为结束，那么就自动添加上
        savePath = !savePath.endsWith("/") ? savePath + "/" : savePath;

        Path path = Paths.get(savePath + fileName + "/" + TimeFormatUtil.dateToDirectoryName(backupDate));
        Path directories = null;
        try {
            directories = Files.createDirectories(path);
        } catch (IOException e) {
            log.error("创建目录失败：{}", e.getMessage());
        }
        String saveDirectory = null;
        if (directories != null) {
            saveDirectory = directories.toString();
        }
        log.info("备份路径为：{}", saveDirectory);

        // 重新获取表格信息
        tableOfDatabaseService.deleteBydAndh(databaseName, hostIp);
        tableOfDatabaseService.saveTableNameAndTableDesc(hostIp, hostPort, databaseName, dbVersion, username, password);

        List<TablesOfDatabase> tables = tableOfDatabaseService.findAllTablesNoPage(databaseName, hostIp);
        String errMsg = "";
        StringBuilder description = new StringBuilder();
        if (tables != null && !tables.isEmpty()) {
            for (TablesOfDatabase table : tables) {
                String tableName = table.getTableName();
                // 注意：-p 和密码之间不要有空格，否则会弹出 ”Enter password: “
                // /usr/bin/mysqldump -h localjavadev1.ihzsr.cn -u root -pHzsr@123 --databases mlpmisdb > /hzsr/db/mlpmisdb20211023.sql
                // --set-gtid-purged=off 参数用来解决低版本mysql无法备份的问题
                // --compatible=name 兼容其他数据库或旧版本数据库
                StringBuilder command = new StringBuilder();
                command.append(exeFileName).append(" -h ").append(hostIp)
                        .append(" -P ").append(hostPort)
                        .append(" -u ").append(username)
                        .append(" -p").append(password)
                        .append(" ").append(databaseName)
                        .append(" ").append(tableName)
                        .append(" > ").append(saveDirectory).append("/").append(databaseName).append("-").append(tableName).append(".").append(saveType);
                String cmd = command.toString();
                log.info("备份命令：{}", cmd);


                WindowsCmdResult cmdResult = new WindowsCmdResult();
                try {
                    WindowsCmdResult fromResult = WindowsCmdUtils.executeLinuxCmd(cmd.trim());
                    cmdResult.setFlag(fromResult.getFlag());
                    cmdResult.setMsg(fromResult.getMsg());
                } catch (Exception e) {
                    log.error("命令执行失败 => {}", e.getMessage());
                    cmdResult.setFlag(false);
                    cmdResult.setMsg(e.getMessage());
                }
                if (!cmdResult.getFlag()) {
                    log.error("命令执行失败 => {}", cmdResult.getMsg());

                    // 回滚 /hzsr/db/mlpmisdb/2011-22-06
                    File file = new File(Objects.requireNonNull(saveDirectory).substring(0, saveDirectory.length() - 5));

                    log.info("回滚目录：{}" + file.getAbsolutePath());
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File f : files) {
                            if (f.isDirectory() && f.getName().equals(TimeFormatUtil.dateToDirHM(backupDate))) {
                                log.info("要删除的目录绝对值：{}", f.getAbsolutePath());
                                log.info("要清空的目录名字：{}", f.getName());
                                FileUtil.deleteDirectory(f);
                                try {
                                    f.delete();
                                } catch (Exception e) {
                                    log.error("清空目录失败：{}", e.getMessage());
                                }
                            }
                        }
                    }

                    backupRecordService.insertBackupRecord(hostIp, hostPort, databaseName, backupDate, username, password, (byte) 1, (byte) 0,
                            null, null, null, null, cmdResult.getMsg(), id);
                    long end = System.currentTimeMillis();
                    description.append("定时备份数据库任务{").append(databaseName)
                            .append("}").append("=>失败");
                    jobExeDetailService.insertSelective(backupDate, Math.round(end - start), description.toString());
                    return;
                }
            }


            // 修改最后备份时间
            databaseConfigService.updateBackupTime(id);
            long end = System.currentTimeMillis();
            double backupFee = end - start;

            log.info("{} 备份成功, 备份耗时：{}", databaseName, TimeFormatUtil.secondsToFormat(backupFee / 1000));
            //===================将备份数据添加到备份记录中=======================
            backupRecordService.insertBackupRecord(hostIp, hostPort, databaseName, backupDate, username, password, (byte) 1, (byte) 1,
                    saveDirectory, Math.round(backupFee), saveType, tables.size(), "", id);
            description.append("定时备份数据库任务{").append(databaseName)
                    .append("}").append("=>成功");
            jobExeDetailService.insertSelective(backupDate, Math.round(backupFee), description.toString());
        }
    }



    // 每隔1小时清理任务
    @Async
    @Scheduled(cron = "0 0 */1 * * ?")
    public void clearBackupLog() {
        log.info("现在时间是：{}, 清理数据库备份任务线程为：{}", LocalDateTime.now().toLocalDate(), Thread.currentThread().getName());
        Date clearTime = new Date();
        List<DatabaseConfig> allTasks = databaseConfigService.findAllTasks();

        // 用于存放数据库以及对应的备份周期
        Map<String, Integer> db_to_cycle = new ConcurrentHashMap<>();

        String databaseName;
        Integer backupCycle;
        for (DatabaseConfig task : allTasks) {
            log.debug("数据库连接信息 => {}", task.toString());
            databaseName = task.getDatabaseName();
            Integer databaseId = task.getId();
            backupCycle = ruleService.getBackupCycle(databaseId);
            log.info("backupCycle => {}", backupCycle);
            if (Objects.nonNull(backupCycle)) {
                db_to_cycle.put(databaseName, backupCycle);
            }
        }

        for (Map.Entry<String, Integer> entry : db_to_cycle.entrySet()) {
            databaseName = entry.getKey();
            backupCycle = entry.getValue();
            log.debug("数据库：{}, 备份周期：{}", databaseName, backupCycle);
            List<BackupRecord> recordList = backupRecordService.findRecordByName(databaseName);

            for (BackupRecord record : recordList) {
                Date backupTime = record.getBackupTime();

                long from = backupTime.getTime();
                long to = clearTime.getTime();
                log.debug("备份时间：{}, 清理时间：{}", from, to);

                int distinctHours = (int) ((to - from) / 1000 / 60 / 60);
                log.debug("数据库：{}, 路径：{}, 已相差小时：{}", record.getBackupDatabase(), record.getBackupPath(), distinctHours);
                if (!StringUtils.isEmpty(record.getBackupPath()) && distinctHours > backupCycle * 24) {
                    new Thread(() -> {

                        log.info("现在是：{}, 开始清理目录：{}", TimeFormatUtil.dateToNormal(clearTime), record.getBackupPath());
                        clearSuccess(record, clearTime);

                    }, "清理成功备份记录线程[" + record.getBackupPath() + "]").start();
                } else if (StringUtils.isEmpty(record.getBackupPath()) && distinctHours > backupCycle * 24) {
                    new Thread(() -> {

                        log.info("现在是：{}, 开始清理失败记录：{}", TimeFormatUtil.dateToNormal(clearTime), record.getId());
                        clearFailure(record);

                    }, "清理失败备份记录线程["+ record.getId() +"]").start();
                }
            }
        }
    }

    // 清理备份失败的记录
    private synchronized void clearFailure(BackupRecord record) {
        log.info("record.getId() => {}, 数据库名 => {}", record.getId(), record.getBackupDatabase());
        // 假删除
        backupRecordService.deleteById(record.getId());
        backupFileService.deleteByRecord(record.getId());
    }

    // 清理备份成功的记录
    private synchronized void clearSuccess(BackupRecord record, Date clearTime) {
        long start = System.currentTimeMillis();
        File file = new File(record.getBackupPath());
        log.info("清理目录为：{}", file.getAbsolutePath());
        File root = new File(linuxRoot);
        String errMsg = null;
        long origin = root.getUsableSpace();

        if (file.exists() && file.isDirectory()) {
            try {
                FileUtil.deleteDirectory(file);
                try {
                    file.delete();
                } catch (Exception e) {
                    log.error("清理失败文件异常：{}", e.getMessage());
                }
            } catch (Exception e) {
                errMsg = e.getMessage();
                log.error("清理失败目录异常：{}", e.getMessage());
            }
        } else {
            errMsg = "目录不存在";
            log.error("目录：{} 不存在", file.getAbsolutePath());
        }

        long now = root.getUsableSpace();
        ClearLog clearLog = new ClearLog();
        clearLog.setDatabaseName(record.getBackupDatabase());
        clearLog.setBackupTime(record.getBackupTime());
        clearLog.setBackupPath(record.getBackupPath());
        clearLog.setBackupSize(record.getBackupSize());
        clearLog.setClearTime(clearTime);

        StringBuilder description = new StringBuilder();
        if (StringUtils.isEmpty(errMsg)) {

            clearLog.setClearRes((byte) 1);
            clearLog.setFreeSpace(now - origin);
            clearLog.setUpdateTime(new Date());
            // 假删除
            backupRecordService.deleteById(record.getId());
            backupFileService.deleteByRecord(record.getId());
            description.append("清理备份记录任务: {").append(record.getBackupDatabase())
                    .append("}, 目录为: {").append(record.getBackupPath())
                    .append("}").append("=>成功");
            log.info(record.getBackupPath() + " 清理成功");
        } else {
            clearLog.setClearRes((byte) 0);
            clearLog.setExceptionMsg(errMsg);
            // 假删除
            backupRecordService.deleteById(record.getId());
            backupFileService.deleteByRecord(record.getId());
            description.append("清理备份记录任务: {").append(record.getBackupDatabase())
                    .append("}, 目录为: {").append(record.getBackupPath())
                    .append("}").append("=>失败").append("=>")
                    .append(errMsg);
            log.info(record.getBackupPath() + " 清理异常");
        }

        clearLogService.save(clearLog);
        // 如果在天目录下已删除全部子目录,那么也删除时间目录
        String dayPath = record.getBackupPath().substring(0, record.getBackupPath().length()-4);
        file = new File(dayPath);
        if (Objects.requireNonNull(file.listFiles()).length == 0) {
            log.info("{} 目录已空, 删除", dayPath);
            try {
                file.delete();
            } catch (Exception e) {
                log.error("删除空目录异常: {}", e.getMessage());
            }
        }
        long end = System.currentTimeMillis();

        jobExeDetailService.insertSelective(clearTime, Math.round(end - start), description.toString());
    }
}
