package com.mapper;

import com.pojo.BackupRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BackupRecordMapper {

    /**
     * 添加新的备份记录
     * @param record 新的备份记录
     * @return insert count
     */
    int insert(BackupRecord record);



    /**
     * 分页获取备份记录
     * @param start 分页第一个参数
     * @param rows  分页第二个参数
     * @return List<BackupRecord> 连接信息列表
     * @author Fang Ruichuan
     * @date 2021/10/25 14:30
     */
    List<BackupRecord> findAllRecord(@Param("start") Integer start, @Param("rows") Integer rows,
                                     @Param("database") String database, @Param("res") String res);


    /**
     * 获取备份记录总数
     *
     * @return int 备份记录条数
     * @author Fang Ruichuan
     * @date 2021/10/25 14:38
     */
    Long getRecordTotal(@Param("database") String database, @Param("res") String res);


    /**
     * 去重获取所有数据库名称
     *
     * @return List<String>
     * @author Fang Ruichuan
     * @date 2021/11/8 9:07
     */
    List<BackupRecord> findDatabase();

    List<BackupRecord> findRecordByName(String databaseName);

    @Delete("delete from myweb_dbbackup.dbbackup_backup_record where id = #{id};")
    void deleteById(Integer id);
}
