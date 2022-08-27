package com.pojo;




public class TableStructure {
    /**
     * TODO 主键ID
     */
    private Integer id;

    /**
     * TODO 服务器地址
     */
    private String hostIp;

    /**
     * TODO 数据库名
     */
    private String databaseName;

    /**
     * TODO 表名
     */
    private String tableName;

    /**
     * TODO 字段名
     */
    private String columnName;

    /**
     * TODO 字段类型
     */
    private String columnType;

    /**
     * TODO 字段描述
     */
    private String columnComment;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
