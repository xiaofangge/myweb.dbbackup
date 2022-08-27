package com.pojo;


import java.util.Date;

public class BackupRecord {

    /**
     * TODO 主键ID
     */
    private Integer id;

    /**
     * TODO 备份数据库
     */
    private String backupDatabase;

    /**
     * TODO 备份时间
     */
    private Date backupTime;

    /**
     * TODO 备份方式
     */
    private Byte backupMethod;

    /**
     * TODO 备份结果
     */
    private Byte backupRes;

    /**
     * TODO 备份路径
     */
    private String backupPath;

    /**
     * TODO 备份大小
     */
    private Long backupSize;

    /**
     * TODO 备份时长
     */
    private Long backupFee;

    /**
     * TODO 备份格式
     */
    private String backupType;

    /**
     * TODO 磁盘空间剩余
     */
    private Long usableSpace;

    /**
     * TODO 备注
     */
    private String remark;

    /**
     * TODO 文件数
     */
    private Integer fileCount;

    /**
     * TODO 错误日志
     */
    private String errLog;

    /**
     * TODO 清理标记（假删除）
     */
    private Boolean clearFlag;


    public Boolean getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(Boolean clearFlag) {
        this.clearFlag = clearFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackupDatabase() {
        return backupDatabase;
    }

    public void setBackupDatabase(String backupDatabase) {
        this.backupDatabase = backupDatabase;
    }

    public Date getBackupTime() {
        return backupTime == null ? null : (Date) backupTime.clone();
    }

    public void setBackupTime(Date backupTime) {
        this.backupTime = backupTime == null ? null : (Date) backupTime.clone();
    }

    public Byte getBackupMethod() {
        return backupMethod;
    }

    public void setBackupMethod(Byte backupMethod) {
        this.backupMethod = backupMethod;
    }

    public Byte getBackupRes() {
        return backupRes;
    }

    public void setBackupRes(Byte backupRes) {
        this.backupRes = backupRes;
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

    public Long getBackupFee() {
        return backupFee;
    }

    public void setBackupFee(Long backupFee) {
        this.backupFee = backupFee;
    }

    public String getBackupType() {
        return backupType;
    }

    public void setBackupType(String backupType) {
        this.backupType = backupType;
    }

    public Long getUsableSpace() {
        return usableSpace;
    }

    public void setUsableSpace(Long usableSpace) {
        this.usableSpace = usableSpace;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public String getErrLog() {
        return errLog;
    }

    public void setErrLog(String errLog) {
        this.errLog = errLog;
    }
}
