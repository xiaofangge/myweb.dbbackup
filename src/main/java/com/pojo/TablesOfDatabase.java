package com.pojo;




public class TablesOfDatabase {

    /**
     * TODO 主键ID
     */
    private Integer id;

    /**
     * TODO 表名
     */
    private String tableName;

    /**
     * TODO 表描述
     */
    private String tableDesc;

    /**
     * TODO 记录行数
     */
    private Integer recordNum;

    /**
     * TODO 所属服务器地址
     */
    private String hostIp;

    /**
     * TODO 所属数据库名称
     */
    private String databaseName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
