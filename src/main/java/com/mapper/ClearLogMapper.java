package com.mapper;


import com.pojo.ClearLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClearLogMapper {
    void save(ClearLog clearLog);

    List<ClearLog> clearLogList(@Param("start") Integer start, @Param("limit") Integer limit);

    Long clearLogCount();
}
