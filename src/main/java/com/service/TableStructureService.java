package com.service;


import com.pojo.TableStructure;

import java.util.List;

public interface TableStructureService {
    /**
     * 根据服务器地址，数据库名称以及表名删除原来的
     *
     * @param hostIp       服务器地址
     * @param databaseName 数据库名称
     * @param tableName    表名
     * @author Fang Ruichuan
     * @date 2021/11/3 17:33
     */
    void deleteOld(String hostIp, String databaseName, String tableName);

    /**
     * 连接上该地址之后查询表结构并添加到项目数据库中
     *
     * @param hostIp       服务器地址
     * @param databaseName 数据库名
     * @param tableName    表名
     * @param username     用户名
     * @param password     密码
     * @author Fang Ruichuan
     * @date 2021/11/3 17:34
     */
    void saveTableStructure(String hostIp, String hostPort, String databaseName, Integer dbVersion, String tableName, String username, String password);

    /**
     * 分页获取表结构信息
     *
     * @param page         页
     * @param limit        每页记录条数
     * @param hostIp       服务器地址
     * @param databaseName 数据库名
     * @param tableName    表名
     * @return List<TableStructure>
     * @author Fang Ruichuan
     * @date 2021/11/3 17:38
     */
    List<TableStructure> findStructureInfo(Integer page, Integer limit, String hostIp, String databaseName, String tableName);

    // 获取表格中的字段总数
    Long structureCount(String hostIp, String databaseName, String tableName);

}
