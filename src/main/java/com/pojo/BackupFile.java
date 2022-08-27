package com.pojo;




public class BackupFile {

    /**
     * TODO 主键ID
     */
    private Integer id;

    /**
     * TODO 文件名
     */
    private String fileName;

    /**
     * TODO 表格名
     */
    private String tableName;

    /**
     * TODO 表行数
     */
    private Long tableLine;

    /**
     * TODO 文件大小
     */
    private Long fileSize;

    /**
     * TODO 所属记录ID
     */
    private Integer recordId;

    /**
     * TODO 清理标记（用于做假删除）
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getTableLine() {
        return tableLine;
    }

    public void setTableLine(Long tableLine) {
        this.tableLine = tableLine;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}
