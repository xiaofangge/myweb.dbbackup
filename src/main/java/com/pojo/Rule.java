package com.pojo;



public class Rule {
    /**
     * TODO 主键ID
     */
    private Integer id;

    /**
     * TODO 备份星期
     */
    private Integer backupWeek;

    /**
     * TODO 备份具体时间
     */
    private String backupWholePoint;

    /**
     * TODO 备份周期
     */
    private Integer backupCycle;

    /**
     * TODO 所属连接数据库ID
     */
    private Integer databaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBackupWeek() {
        return backupWeek;
    }

    public void setBackupWeek(Integer backupWeek) {
        this.backupWeek = backupWeek;
    }

    public String getBackupWholePoint() {
        return backupWholePoint;
    }

    public void setBackupWholePoint(String backupWholePoint) {
        this.backupWholePoint = backupWholePoint;
    }

    public Integer getBackupCycle() {
        return backupCycle;
    }

    public void setBackupCycle(Integer backupCycle) {
        this.backupCycle = backupCycle;
    }

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }
}
