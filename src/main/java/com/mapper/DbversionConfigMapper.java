package com.mapper;

import com.pojo.DbversionConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DbversionConfigMapper {
    List<DbversionConfig> dbversionList();
}
