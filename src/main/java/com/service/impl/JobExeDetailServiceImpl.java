package com.service.impl;

import com.mapper.JobExeDetailMapper;
import com.pojo.JobExeDetail;
import com.service.JobExeDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class JobExeDetailServiceImpl implements JobExeDetailService {

    @Resource(name = "jobExeDetailMapper")
    private JobExeDetailMapper jobExeDetailMapper;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        jobExeDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(JobExeDetail record) {
        jobExeDetailMapper.insert(record);
    }

    @Override
    public void insertSelective(Date exeTime, long exeFee, String description) {
        JobExeDetail jobExeDetail = new JobExeDetail();
        jobExeDetail.setExeTime(exeTime);
        jobExeDetail.setExeFee(exeFee);
        jobExeDetail.setDescription(description);
        jobExeDetailMapper.insertSelective(jobExeDetail);
    }

    @Override
    public JobExeDetail selectByPrimaryKey(Integer id) {
        return jobExeDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(JobExeDetail record) {
        jobExeDetailMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void updateByPrimaryKey(JobExeDetail record) {
        jobExeDetailMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<JobExeDetail> jobExeDetailList(Integer page, Integer limit, String exeTime) {
        return jobExeDetailMapper.jobExeDetailList((page - 1) * limit, limit, exeTime);
    }

    @Override
    public Long jobExeDetailCount(String exeTime) {
        return jobExeDetailMapper.jobExeDetailCount(exeTime);
    }
}
