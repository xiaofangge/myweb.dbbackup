package com.service;


import com.pojo.DatabaseConfig;

import java.util.List;

public interface DatabaseConfigService {

    DatabaseConfig select(Integer id);


    int changeExecFlag(Integer dbId, Integer exec);

    /**
     * 添加新的备份信息
     *
     * @param record 新的备份信息
     * @return insert count
     */
    int insert(DatabaseConfig record);


    /**
     * 修改备份信息
     *
     * @param record 修改后的备份信息
     * @return update count
     */
    int updateByPrimaryKeySelective(DatabaseConfig record);

    /**
     * 分页获取连接信息列表
     *
     * @param page 第几页
     * @param rows 每页的数据记录数
     * @return List<HzsrbackupCopyList> 连接信息列表
     * @author Fang Ruichuan
     * @date 2021/10/25 14:30
     */
    List<DatabaseConfig> findConnectionInfo(Integer page, Integer rows);


    /**
     * 获取连接信息
     * @author Fang Ruichuan
     * @date 2021/10/25 14:38
     * @return int
     */
    Long getConnectionInfoTotal();

    /**
     * 根据id删除连接信息
     * @author Fang Ruichuan
     * @date 2021/10/25 15:55
     * @param id 主键id
     * @return int
     */
    int deleteInfoById(Integer id);


    /**
     * 根据id修改时间
     * @author Fang Ruichuan
     * @date 2021/10/26 16:17
     * @param id 主键
     */
    void updateBackupTime(Integer id);

    List<DatabaseConfig> findAllTasks();

}
