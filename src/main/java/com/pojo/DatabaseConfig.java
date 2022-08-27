package com.pojo;

import java.util.Date;


/**
 * 备份列表
 */
public class DatabaseConfig {

    @Override
    public String toString() {
        return "DatabaseConfig{" +
                "id=" + id +
                ", hostIp='" + hostIp + '\'' +
                ", hostPort=" + hostPort +
                ", databaseName='" + databaseName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", savePath='" + savePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", saveType='" + saveType + '\'' +
                ", copyTime=" + copyTime +
                ", largestSize=" + largestSize +
                ", execFlag=" + execFlag +
                ", dbVersion=" + dbVersion +
                ", dbversionConfig=" + dbversionConfig +
                '}';
    }

    /**
     * 主键
     */
    private Integer id;

    /**
     * 服务器地址
     */
    private String hostIp;


    /**
     * 端口号
     */
    private Integer hostPort;


    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 数据库用户名
     */
    private String username;


    /**
     * 数据库连接密码
     */
    private String password;


    /**
    * 保存路径
    */
    private String savePath;

    /**
    * 保存文件名称
    */
    private String fileName;

    /**
     * 备份格式（sql/dump）
     */
    private String saveType;

    /**
     * 最后备份时间
     */
    private Date copyTime;

    /**
     * 最大备份大小
     */
    private Long largestSize;

    /**
     * 定时任务状态(1-on, 0-off)
     */
    private Byte execFlag;

    /**
     * 数据库版本ID
     */
    private Integer dbVersion;

    /**
     * 数据库版本
     */
    private DbversionConfig dbversionConfig;

    public DbversionConfig getDbversionConfig() {
        return dbversionConfig;
    }

    public void setDbversionConfig(DbversionConfig dbversionConfig) {
        this.dbversionConfig = dbversionConfig;
    }

    public Integer getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Byte getExecFlag() {
        return execFlag;
    }

    public void setExecFlag(Byte execFlag) {
        this.execFlag = execFlag;
    }

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

    public Integer getHostPort() {
        return hostPort;
    }

    public void setHostPort(Integer hostPort) {
        this.hostPort = hostPort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public Date getCopyTime() {
        return copyTime == null ? null : (Date) copyTime.clone();
    }

    public void setCopyTime(Date copyTime) {
        this.copyTime = copyTime == null ? null : (Date) copyTime.clone();
    }

    public Long getLargestSize() {
        return largestSize;
    }

    public void setLargestSize(Long largestSize) {
        this.largestSize = largestSize;
    }
}