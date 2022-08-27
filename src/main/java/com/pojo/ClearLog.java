package com.pojo;

import java.util.Date;

public class ClearLog {
    private Integer id;
    private String databaseName;
    private Date backupTime;
    private String backupPath;
    private Long backupSize;
    private Date clearTime;
    private Byte clearRes;
    private Long freeSpace;
    private Date updateTime;
    private String exceptionMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Date getBackupTime() {
        return backupTime == null ? null : (Date) backupTime.clone();
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime == null ? null : (Date) backupTime.clone();
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }

    public Long getBackupSize() {
        return backupSize;
    }

    public void setBackupSize(Long backupSize) {
        this.backupSize = backupSize;
    }

    public Date getClearTime() {
        return clearTime == null ? null : (Date) clearTime.clone();
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime == null ? null : (Date) clearTime.clone();
    }

    public Byte getClearRes() {
        return clearRes;
    }

    public void setClearRes(Byte clearRes) {
        this.clearRes = clearRes;
    }

    public Long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Long freeSpace) {
        this.freeSpace = freeSpace;
    }

    public Date getUpdateTime() {
        return updateTime == null ? null : (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }
}
