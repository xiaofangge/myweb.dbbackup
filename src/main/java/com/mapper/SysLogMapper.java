package com.mapper;

import com.pojo.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLog, Long> {

    List<SysLog> sysLogList(@Param("start") Integer start, @Param("limit") Integer limit);

    Long sysLogCount();
}
