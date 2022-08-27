package com.service;


import com.pojo.JobExeDetail;

import java.util.Date;
import java.util.List;

public interface JobExeDetailService {
    void deleteByPrimaryKey(Integer id);

    void insert(JobExeDetail record);

    /**
     * 插入定时任务执行情况
     * @author Fang Ruichuan
     * @date 2021/12/28 16:53
     * @param exeTime       执行时间
     * @param exeFee        执行时长
     * @param description   任务描述
     */
    void insertSelective(Date exeTime, long exeFee, String description);

    JobExeDetail selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(JobExeDetail record);

    void updateByPrimaryKey(JobExeDetail record);

    List<JobExeDetail> jobExeDetailList(Integer page, Integer limit, String exeTime);

    Long jobExeDetailCount(String exeTime);

}
