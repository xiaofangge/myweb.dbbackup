package com.mapper;

import com.pojo.JobExeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface JobExeDetailMapper {
    void deleteByPrimaryKey(Integer id);

    void insert(JobExeDetail record);

    void insertSelective(JobExeDetail record);

    JobExeDetail selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(JobExeDetail record);

    void updateByPrimaryKey(JobExeDetail record);

    List<JobExeDetail> jobExeDetailList(@Param("start") Integer start, @Param("limit") Integer limit,
                                        @Param("exeTime") String exeTime);

    Long jobExeDetailCount(@Param("exeTime") String exeTime);
}