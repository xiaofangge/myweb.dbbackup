package com.service.impl;


import com.mapper.BackupFileMapper;
import com.mapper.BackupRecordMapper;
import com.mapper.DatabaseConfigMapper;
import com.pojo.BackupFile;
import com.pojo.BackupRecord;
import com.pojo.DatabaseConfig;
import com.service.BackupRecordService;
import com.utils.DriverUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.List;


@SuppressWarnings("all")
@Service
@Transactional
public class BackupRecordServiceImpl implements BackupRecordService {

    private final static Logger log = LoggerFactory.getLogger(BackupRecordServiceImpl.class);

    @Autowired
    private BackupRecordMapper backupRecordMapper;

    @Autowired
    private BackupFileMapper fileMapper;

    @Autowired
    private DatabaseConfigMapper databaseConfigMapper;

    @Override
    public boolean insert(BackupRecord record) {
        boolean flag = false;
        if (backupRecordMapper.insert(record) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<BackupRecord> findAllRecord(Integer page, Integer rows, String database, String res) {
        Integer start = (page - 1) * rows;
        return backupRecordMapper.findAllRecord(start, rows, database, res);
    }

    @Override
    public Long getRecordTotal(String database, String res) {
        return backupRecordMapper.getRecordTotal(database, res);
    }

    /**
     * 将备份数据添加到备份记录中
     *
     * @param totalPath
     * @param backupFee
     * @param saveType
     * @author Fang Ruichuan
     * @date 2021/10/26 14:53
     */
    @Override
    public synchronized void insertBackupRecord(String hostIp, Integer hostPort, String databaseName, Date backupTime,
                                                String username, String password, Byte backupMethod, Byte backupRes,
                                                String saveDirectory, Long backupFee, String backupType, Integer fileCount,
                                                String errMsg, Integer databaseId) {
        BackupRecord backupRecord = new BackupRecord();
        backupRecord.setBackupDatabase(databaseName);
        backupRecord.setBackupTime(backupTime);
        backupRecord.setBackupMethod(backupMethod);
        backupRecord.setBackupRes(backupRes);
        backupRecord.setErrLog(errMsg);

        log.info("磁盘路径：{}", saveDirectory.substring(0, 3));
        File disPartition = new File(saveDirectory.substring(0, 3));
        // 添加磁盘剩余可用空间
        backupRecord.setUsableSpace(disPartition.getUsableSpace());

        File[] files = null;
        // 备份成功才添加以下信息
        if (saveDirectory != null) {
            File folder = new File(saveDirectory);
            Long backupSize = 0L;
            files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    backupSize += file.length();
                } else if (file.isDirectory()) {
                    continue;
                }
            }
            backupRecord.setBackupPath(saveDirectory);
            backupRecord.setFileCount(fileCount);
            backupRecord.setBackupSize(backupSize);
            backupRecord.setBackupFee(backupFee);
            backupRecord.setBackupType(backupType);
            backupRecord.setRemark(null);
            databaseConfigMapper.updateBackupLargestSize(databaseId, backupSize);
        }

        this.insert(backupRecord);

        DatabaseConfig config = databaseConfigMapper.select(databaseId);
        Integer dbVersion = config.getDbVersion();

        // 将备份的每一个文件信息把存到hzsrbackup_save_file表中
        if (files != null && backupRes == 1) {
            for (File file : files) {
                if (file.isFile()) {
                    BackupFile backupFileRecord = new BackupFile();
                    backupFileRecord.setRecordId(backupRecord.getId());
                    String fileName = file.getName();
                    log.info("文件名：{}", fileName);
                    int start = databaseName.length() + 1;
                    log.info("start : {}", start);
                    int end = fileName.lastIndexOf(".");
                    log.info("end : {}", end);
                    String tableName = fileName.substring(start, end);
                    log.info("表名：{}", tableName);
                    backupFileRecord.setFileName(fileName);
                    backupFileRecord.setTableName(tableName);
                    backupFileRecord.setRecordId(backupRecord.getId());


                    String url = "jdbc:mysql://" + hostIp + ":" + hostPort + "/" + databaseName +
                            "?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=true&useUnicode=true";

                    // 声明Connection对象
                    Connection con = null;
                    // 声明PreparedStatement对象
                    PreparedStatement st = null;
                    ResultSet rs = null;
                    long tableLine = 0L;
                    try {
                        if (dbVersion.equals(1)) {
                            Class.forName(DriverUtil.DRIVER_FOR_8);
                        } else if (dbVersion.equals(2)) {
                            Class.forName(DriverUtil.DRIVER_FOR_5);
                        }
                        con = DriverManager.getConnection(url, username, password);
                        String sql = "select count(1) from " + tableName;
                        st = con.prepareStatement(sql);
                        rs = st.executeQuery();
                        if (rs.next()) {
                            tableLine = rs.getInt(1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (st != null) {
                            try {
                                st.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (con != null) {
                            try {
                                con.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    backupFileRecord.setTableLine(tableLine);
                    backupFileRecord.setFileSize(file.length());
                    fileMapper.insert(backupFileRecord);
                } else if (file.isDirectory()) {
                    continue;
                }
            }
        }

    }

    @Override
    public List<BackupRecord> findDatabase() {
        return backupRecordMapper.findDatabase();
    }

    @Override
    public List<BackupRecord> findRecordByName(String databaseName) {
        return backupRecordMapper.findRecordByName(databaseName);
    }

    @Override
    public void deleteById(Integer id) {
        backupRecordMapper.deleteById(id);
    }

}
