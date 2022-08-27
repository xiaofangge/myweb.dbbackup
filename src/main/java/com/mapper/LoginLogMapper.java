package com.mapper;

import com.pojo.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginLogMapper {

    void insert(LoginLog loginLog);

    List<LoginLog> findLogList(@Param("start") Integer start, @Param("limit") Integer limit);

    @Select("select count(1) from myweb_dbbackup.dbbackup_login_log;")
    long logCount();
}
