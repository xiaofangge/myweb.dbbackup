package com.pojo;

public class DbversionConfig {
    private Integer id;
    private String dbVersion;
    private String exeFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getExeFileName() {
        return exeFileName;
    }

    public void setExeFileName(String exeFileName) {
        this.exeFileName = exeFileName;
    }
}
