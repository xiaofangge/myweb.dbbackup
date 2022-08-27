package com.mapper;


import com.pojo.TablesOfDatabase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository  // 用在持久层上，将接口的实现类交给Spring管理
public interface TableOfDatabaseMapper {

    /**
     * 向数据表中插入数据表表名和信息
     *
     * @param tablesOfDatabase 参数
     * @author Fang Ruichuan
     * @date 2021/10/27 14:24
     */
    void insert(TablesOfDatabase tablesOfDatabase);



    /**
     * 根据服务器地址和数据库名称查找所有的数据表不分页
     * @author Fang Ruichuan
     * @date 2021/10/30 14:58
     * @return List<TablesOfDatabase>
     */
    List<TablesOfDatabase> findAllTablesNoPage(@Param("databaseName") String databaseName, @Param("hostIp") String hostIp);

    /**
     * 根据服务器地址和数据库名称查找所有的数据表
     * @author Fang Ruichuan
     * @date 2021/10/27 14:52
     * @return List<TablesOfDatabase>
     */
    List<TablesOfDatabase> findAllTables(@Param("start") Integer start, @Param("rows") Integer rows,
                                         @Param("databaseName") String databaseName, @Param("hostIp") String hostIp);

    @Select("select count(1) from myweb_dbbackup.dbbackup_database_tables_info where database_name = #{databaseName} and host_ip = #{hostIp};")
    Long getCount(@Param("databaseName") String databaseName, @Param("hostIp") String hostIp);


    /**
     * 根据服务器地址和数据库名称删除数据表
     *
     * @param databaseName 数据库名称
     * @param hostIp       服务器地址
     * @author Fang Ruichuan
     * @date 2021/10/30 11:55
     */
    void deleteBydAndh(@Param("databaseName") String databaseName, @Param("hostIp") String hostIp);


}
