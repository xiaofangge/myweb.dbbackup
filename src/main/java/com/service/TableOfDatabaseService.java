package com.service;


import com.pojo.TablesOfDatabase;

import java.util.List;

public interface TableOfDatabaseService {

    /**
     * 向数据表中插入数据表表名和信息
     * @author Fang Ruichuan
     * @date 2021/10/27 14:24
     * @param tablesOfDatabase 数据表
     */
    void insert(TablesOfDatabase tablesOfDatabase);

    /**
     * 根据服务器地址和数据库名称查找所有的数据表不分页
     * @author Fang Ruichuan
     * @date 2021/10/30 14:58
     * @return List<TablesOfDatabase>
     */
    List<TablesOfDatabase> findAllTablesNoPage(String databaseName, String hostIp);



    /**
     * 查找所有的数据表
     * @author Fang Ruichuan
     * @date 2021/10/27 14:52
     * @return List<TablesOfDatabase>
     */
    List<TablesOfDatabase> findAllTables(Integer page, Integer rows, String databaseName, String hostIp);
    Long getCount(String databaseName, String hostIp);

    /**
     * 根据服务器地址和数据库名称删除数据表
     * @author Fang Ruichuan
     * @date 2021/10/30 11:55
     * @param databaseName  数据库名称
     * @param hostIp        服务器地址
     */
    void deleteBydAndh(String databaseName, String hostIp);


    void saveTableNameAndTableDesc(String hostIp, Integer hostPort, String databaseName, Integer dbVersion, String username, String password);

}
