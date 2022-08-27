package com.mapper;

import com.pojo.Rousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RouselMapper {
    @Select("select id, imgUrl, title from myweb_dbbackup.dbbackup_config_rousel order by id")
    List<Rousel> list();
}
