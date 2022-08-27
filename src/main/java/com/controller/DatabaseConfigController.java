package com.controller;

import com.common.anotation.OperationLog;
import com.common.vo.WindowsCmdResult;
import com.common.vo.R;
import com.pojo.DatabaseConfig;
import com.pojo.TablesOfDatabase;
import com.service.BackupRecordService;
import com.service.DatabaseConfigService;
import com.service.RuleService;
import com.service.TableOfDatabaseService;
import com.utils.FileUtil;
import com.utils.WindowsCmdUtils;
import com.utils.OperationType;
import com.utils.TimeFormatUtil;
import org.apache.logging.log4j.util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
@Controller
@RequestMapping("/database")
public class DatabaseConfigController {

    private final static Logger log = LoggerFactory.getLogger(DatabaseConfigController.class);

    @Autowired
    private DatabaseConfigService databaseConfigService;

    @Autowired
    private BackupRecordService backupRecordService;

    @Autowired
    private TableOfDatabaseService tableOfDatabaseService;

    @Autowired
    private RuleService ruleService;


    /**
     * 根据备份数据库任务ID修改定时任务状态
     * @author Fang Ruichuan
     * @date 2021/11/10 16:11
     * @param dbId      备份数据库任务主键Id
     * @param execFlag      定时任务状态（1-on, 0-off）
     * @return Result
     */
    @OperationLog(message = "更改定时任务状态", operation = OperationType.UPDATE)
    @ResponseBody
    @RequestMapping(value = "/changeExecFlag", method = RequestMethod.PUT)
    public R changeExecFlag(@RequestParam("dbId") Integer dbId, @RequestParam("execFlag") String execFlag) {
        int i = databaseConfigService.changeExecFlag(Integer.valueOf(dbId), Integer.parseInt(execFlag));
        if (i > 0) {
            if ("1".equals(execFlag)) {
                return R.success("定时任务已开启");
            } else if ("0".equals(execFlag)) {
                return R.success("定时任务已关闭");
            }
        }
        return R.fail("修改定时任务状态失败");
    }


    @OperationLog(message = "修改数据库连接信息", operation = OperationType.UPDATE)
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public R updateOne(@RequestBody DatabaseConfig connectionInfo) {
        log.info("/database/update请求进来了");
        log.info("前端修改后的连接信息：{}", connectionInfo.toString());
        int row = databaseConfigService.updateByPrimaryKeySelective(connectionInfo);
        if (row > 0) {
            // 由于前端密码为Base64加密过的，所以需要对密码进行解密
            // 将信息修改之后同样插入到表格之中，但是需要先删掉原先的跟服务器地址和数据库相同的数据
            tableOfDatabaseService.deleteBydAndh(connectionInfo.getDatabaseName(), connectionInfo.getHostIp());
            tableOfDatabaseService.saveTableNameAndTableDesc(connectionInfo.getHostIp(), connectionInfo.getHostPort(), connectionInfo.getDatabaseName(),
                    connectionInfo.getDbVersion(), connectionInfo.getUsername(), com.utils.Base64Util.decode(connectionInfo.getPassword()));
            return R.success("修改成功");
        }
        return R.fail("修改失败");
    }


    /**
     * 删除连接信息 RestFul风格
     * @author Fang Ruichuan
     * @date 2021/10/25 16:12
     * @param id 连接信息主键id
     * @return Vo
     */
    @OperationLog(message = "删除数据库连接信息", operation = OperationType.DELETE)
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public R deleteOneById(@PathVariable("id") Integer id) {
        log.info("/database/delete请求进来了");
        log.info("id = {}", id);
        int row = databaseConfigService.deleteInfoById(id);

        if (row > 0) {
            ruleService.deleteRule(id);
            return R.success("删除成功");
        }
        return R.fail("删除失败");
    }

    /**
     * 获取连接信息
     * @author Fang Ruichuan
     * @date 2021/10/25 14:54
     * @param page  页码
     * @param limit 每页的记录数
     * @return Vo
     */
    @OperationLog(message = "获取连接信息", operation = OperationType.QUERY)
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R findAll(@RequestParam(value = "page", required = true) Integer page,
                          @RequestParam(value = "limit", required = true) Integer limit) {
        log.info("/database/list请求进来了");
        log.info("page = {}, limit = {}", page, limit);
        List<DatabaseConfig> connectionInfoList = databaseConfigService.findConnectionInfo(page, limit);
        Long total = databaseConfigService.getConnectionInfoTotal();
        return R.layuiTable(connectionInfoList, total);
    }

    /**
     * 添加需要备份的数据库信息
     * @author Fang Ruichuan
     * @date 2021/10/23 10:58
     * @param hostIp        服务器地址
     * @param username      数据库用户名
     * @param password      数据库密码
     * @param databaseName  要备份的数据库
     * @param savePath      保存路径
     * @param fileName      保存文件名称
     * @param saveType      保存文件格式 (.dump/.sql)
     * @return Vo
     */
    @OperationLog(message = "添加数据库连接信息", operation = OperationType.ADD)
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R add(@RequestParam(value = "hostIp", required = true) String hostIp,
                      @RequestParam(value = "hostPort", defaultValue = "3306") Integer hostPort,
                      @RequestParam(value = "username", required = true) String username,
                      @RequestParam(value = "password", required = true) String password,
                      @RequestParam(value = "databaseName", required = true) String databaseName,
                      @RequestParam(value = "savePath", required = true) String savePath,
                      @RequestParam(value = "fileName", required = true) String fileName,
                      @RequestParam(value = "saveType", required = true) String saveType,
                      @RequestParam(value = "dbVersion", required = true) String dbVersion) {
        log.info("/database/add请求进来了");
        log.info("hostIp = {}, username = {}, password = {}, database = {}, savePath = {}, fileName = {}, saveType = {}",
                hostIp, username, password, databaseName, savePath, fileName, saveType);
        DatabaseConfig copyOne = new DatabaseConfig();
        copyOne.setHostIp(hostIp);
        copyOne.setHostPort(hostPort);
        copyOne.setUsername(username);
        // 将密码进行Base64加密后存进数据库
        copyOne.setPassword(Base64Util.encode(password));
        copyOne.setDatabaseName(databaseName);
        // 如果路径中不以/为结束，那么就自动添加上
        savePath = !savePath.endsWith("\\") ? savePath + "\\" : savePath;
        copyOne.setSavePath(savePath);
        copyOne.setFileName(fileName);
        copyOne.setSaveType(saveType);
        copyOne.setDbVersion(Integer.parseInt(dbVersion));
        int row = databaseConfigService.insert(copyOne);
        if (row > 0) {
            // 在添加之前先删掉原来相同的数据
            tableOfDatabaseService.deleteBydAndh(databaseName, hostIp);
            // 添加成功之后再次连接获取表格信息添加到 hzsrbackup_tables_of_database 表
            tableOfDatabaseService.saveTableNameAndTableDesc(hostIp, hostPort, databaseName, Integer.parseInt(dbVersion), username, password);
            return R.success(databaseName + " 添加成功");
        }
        return R.fail(databaseName + " 添加失败");
    }




    /**
     * 备份数据库
     * @author Fang Ruichuan
     * @date 2021/10/23 10:58
     * @param hostIp        服务器地址
     * @param username      数据库用户名
     * @param password      数据库密码
     * @param databaseName  要备份的数据库
     * @param savePath      保存路径
     * @param fileName      保存文件名称
     * @param saveType      保存文件格式 (.dump/.sql)
     * @return Vo
     */
    @OperationLog(message = "备份数据库", operation = OperationType.BACKUP)
    @ResponseBody
    @RequestMapping(value = "/backups", method = RequestMethod.POST)
    public R backups(@RequestParam(value = "id", required = true) String id,
                          @RequestParam(value = "hostIp", required = true) String hostIp,
                          @RequestParam(value = "hostPort", defaultValue = "3306") Integer hostPort,
                          @RequestParam(value = "username", required = true) String username,
                          @RequestParam(value = "password", required = true) String password,
                          @RequestParam(value = "databaseName", required = true) String databaseName,
                          @RequestParam(value = "dbVersion", required = true) Integer dbVersion,
                          @RequestParam(value = "savePath", required = true) String savePath,
                          @RequestParam(value = "fileName", required = true) String fileName,
                          @RequestParam(value = "saveType", required = true) String saveType) {
        long start = System.currentTimeMillis();
        Date backupTime = new Date();
        log.info("/database/backups请求进来了");
        log.info("id = {}, hostIp = {}, hostPort = {}, username = {}, password = {}, database = {}, savePath = {}, fileName = {}, saveType = {}",
                id, hostIp, hostPort, username, password, databaseName, savePath, fileName, saveType);


        Date backupDate = new Date();

        // 获取执行文件名
        DatabaseConfig select = databaseConfigService.select(Integer.parseInt(id));
        String exeFileName = select.getDbversionConfig().getExeFileName();
        log.info("执行文件名为：{}", exeFileName);

        // 如果路径中不以/为结束，那么就自动添加上
        savePath = !savePath.endsWith("\\") ? savePath + "\\" : savePath;
        Path path = Paths.get(savePath + fileName + "\\" + TimeFormatUtil.dateToDirectoryName(backupDate));
        Path directories = null;
        try {
            directories = Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
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
        if (tables != null && !tables.isEmpty()) {
            for (TablesOfDatabase table : tables) {
                String tableName = table.getTableName();
                // 注意：-p 和密码之间不要有空格，否则会弹出 ”Enter password: “
                // /usr/bin/mysqldump -h localjavadev1.ihzsr.cn -u root -pHzsr@123 --databases mlpmisdb > /hzsr/db/mlpmisdb20211023.sql
                StringBuilder command = new StringBuilder();
                command.append(exeFileName).append(" -h ").append(hostIp)
                        .append(" -P ").append(hostPort)
                        .append(" -u ").append(username)
                        .append(" -p").append(password)
                        .append(" ").append(databaseName)
                        .append(" ").append(tableName)
                        .append(" > ").append(saveDirectory + "\\" + databaseName + "-" + tableName + "." + saveType);
                String cmd = command.toString();
                log.info("备份命令：{}", cmd);

                WindowsCmdResult cmdResult = new WindowsCmdResult();
                try {
                    WindowsCmdResult fromResult = WindowsCmdUtils.executeLinuxCmd(cmd.trim());
                    cmdResult.setFlag(fromResult.getFlag());
                    cmdResult.setMsg(fromResult.getMsg());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    cmdResult.setFlag(false);
                    cmdResult.setMsg(e.getMessage());
                }
                if (!cmdResult.getFlag()) {
                    log.info("命令执行失败");
                    // 回滚
                    File file = new File(saveDirectory.substring(0, saveDirectory.length()-5));
                    log.info("要回滚的目录绝对值：{}", file.getAbsolutePath());
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File f : files) {
                            if (f.isDirectory() && f.getName().equals(TimeFormatUtil.dateToDirHM(backupDate))) {
                                log.info("要清空的目录绝对值：{}", f.getAbsolutePath());
                                log.info("要清空的目录名字：{}", f.getName());
                                FileUtil.deleteDirectory(f);
                                try {
                                    f.delete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    backupRecordService.insertBackupRecord(hostIp, hostPort, databaseName, backupTime, username, password, (byte) 0, (byte) 0,
                            null, null, null, null, cmdResult.getMsg(), Integer.parseInt(id));
                    return R.fail(cmdResult.getMsg());
                }
            }

            // 修改最后备份时间
            databaseConfigService.updateBackupTime(Integer.parseInt(id));

            long end = System.currentTimeMillis();
            double backupFee = end - start;
            log.info(databaseName + "备份成功");

            String backupFeeStr = TimeFormatUtil.secondsToFormat(backupFee / 1000);

            log.info("备份耗时：{}", backupFeeStr);
            //===================将备份数据添加到备份记录中=======================
            backupRecordService.insertBackupRecord(hostIp, hostPort, databaseName, backupTime, username, password, (byte) 0, (byte) 1,
                    saveDirectory, Math.round(backupFee), saveType, tables.size(), "", Integer.parseInt(id));
            return R.success(databaseName + "备份成功，耗时 " + backupFeeStr);
        }
        return R.fail("数据库中没有数据表");
    }
}
