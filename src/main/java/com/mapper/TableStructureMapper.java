package com.mapper;


import com.pojo.TableStructure;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TableStructureMapper {

    /**
     * 新增表结构记录
     *
     * @param structure 表结构 JavaBean
     * @author Fang Ruichuan
     * @date 2021/11/3 17:04
     */
    void insert(TableStructure structure);

    /**
     * 根据服务器地址，数据库名称以及表名删除原来的
     *
     * @param hostIp       服务器地址
     * @param databaseName 数据库名称
     * @param tableName    表名
     * @author Fang Ruichuan
     * @date 2021/11/3 17:33
     */
    @Delete("delete from myweb_dbbackup.dbbackup_table_structure where host_ip = #{hostIp} and database_name = #{databaseName} and table_name = #{tableName} ")
    void deleteOld(@Param("hostIp") String hostIp, @Param("databaseName") String databaseName,
                   @Param("tableName") String tableName);


    /**
     * 分页获取表结构信息
     *
     * @param start        页
     * @param limit        每页记录条数
     * @param hostIp       服务器地址
     * @param databaseName 数据库名
     * @param tableName    表名
     * @return List<TableStructure>
     * @author Fang Ruichuan
     * @date 2021/11/3 17:38
     */
    List<TableStructure> findStructureInfo(@Param("start") Integer start,
                                           @Param("limit") Integer limit,
                                           @Param("hostIp") String hostIp,
                                           @Param("databaseName") String databaseName,
                                           @Param("tableName") String tableName);

    // 获取表格中的字段总数
    @Select("select count(1) from myweb_dbbackup.dbbackup_table_structure where host_ip = #{hostIp} and database_name = #{databaseName} and table_name = #{tableName} ")
    Long structureCount(@Param("hostIp") String hostIp, @Param("databaseName") String databaseName,
                       @Param("tableName") String tableName);
}
