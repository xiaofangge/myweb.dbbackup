package com.service;


import com.pojo.BackupRecord;

import java.util.Date;
import java.util.List;

public interface BackupRecordService {
    /**
     * 添加新的备份记录
     * @param record 新的备份记录
     * @return insert count
     */
    boolean insert(BackupRecord record);


    /**
     * 分页获取备份记录
     * @param page 分页第一个参数
     * @param rows 分页第二个参数
     * @return List<BackupRecord> 连接信息列表
     * @author Fang Ruichuan
     * @date 2021/10/25 14:30
     */
    List<BackupRecord> findAllRecord(Integer page, Integer rows, String database, String res);


    /**
     * 获取备份记录总数
     *
     * @return int 备份记录条数
     * @author Fang Ruichuan
     * @date 2021/10/25 14:38
     */
    Long getRecordTotal(String database, String res);


    /**
     * 将备份记录保存到数据库中
     *
     * @param hostIp           服务器地址
     * @param hostPort         端口
     * @param databaseName     数据库
     * @param backupTime       备份时间
     * @param username         用户名
     * @param password         密码
     * @param backupMethod     备份方式
     * @param backupRes        备份结果
     * @param saveDirectory    保存目录
     * @param backupFee        备份耗时
     * @param backupType       备份格式
     * @param fileCount        文件个数
     * @param errMsg           失败异常
     * @param databaseId       数据库信息ID
     * @author Fang Ruichuan
     * @date 2021/11/9 11:18
     */
    void insertBackupRecord(String hostIp, Integer hostPort, String databaseName, Date backupTime,
                            String username, String password,
                            Byte backupMethod, Byte backupRes,
                            String saveDirectory, Long backupFee, String backupType,
                            Integer fileCount, String errMsg, Integer databaseId);

    /**
     * 去重查询所有的数据库名称
     *
     * @return List<String>
     * @author Fang Ruichuan
     * @date 2021/11/8 9:05
     */
    List<BackupRecord> findDatabase();

    List<BackupRecord> findRecordByName(String databaseName);

    void deleteById(Integer id);
}
