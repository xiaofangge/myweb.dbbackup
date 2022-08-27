package com.pojo;


import java.util.Date;

/**
* 定时任务执行情况表
*/
public class JobExeDetail {
    /**
    * 主键ID
    */
    private Integer id;

    /**
    * 任务执行时间
    */
    private Date exeTime;

    /**
    * 任务执行时长
    */
    private Long exeFee;

    /**
    * 任务描述
    */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExeTime() {
        return exeTime == null ? null : (Date) exeTime.clone();
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime == null ? null : (Date) exeTime.clone();
    }

    public Long getExeFee() {
        return exeFee;
    }

    public void setExeFee(Long exeFee) {
        this.exeFee = exeFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}