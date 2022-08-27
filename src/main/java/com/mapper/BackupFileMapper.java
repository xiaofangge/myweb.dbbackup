package com.mapper;


import com.pojo.BackupFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BackupFileMapper {


    void insert(BackupFile backupFile);

    List<BackupFile> findPageFile(@Param("recordId") Integer recordId, @Param("tableName") String tableName,
                                  @Param("orderBy") String orderBy, @Param("orderMethod") String orderMethod,
                                  @Param("start") Integer start, @Param("rows") Integer rows);

    Long getPageTotal(@Param("recordId") Integer recordId, @Param("tableName") String tableName,
                     @Param("orderBy") String orderBy, @Param("orderMethod") String orderMethod);

    @Delete("delete from myweb_dbbackup.dbbackup_backup_files where record_id = #{recordId}; ")
    void deleteByRecord(Integer recordId);
}
