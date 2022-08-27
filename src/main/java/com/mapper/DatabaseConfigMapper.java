package com.mapper;

import com.pojo.DatabaseConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DatabaseConfigMapper {

    /**
     * 根据id查询备份任务
     *
     * @param id 主键ID
     * @return HzsrbackupCopyList
     * @author Fang Ruichuan
     * @date 2021/11/2 14:21
     */
    DatabaseConfig select(@Param("id") Integer id);

    /**
     * 添加新的备份信息
     *
     * @param record 新的备份信息
     * @return insert count
     */
    int insert(DatabaseConfig record);


    /**
     * 修改备份信息
     *
     * @param record 修改后的备份信息
     * @return update count
     */
    int updateByPrimaryKeySelective(DatabaseConfig record);


    /**
     * 分页获取连接信息列表
     *
     * @param start 分页第一个参数
     * @param rows  分页第二个参数
     * @return List<HzsrbackupCopyList> 连接信息列表
     * @author Fang Ruichuan
     * @date 2021/10/25 14:30
     */
    List<DatabaseConfig> findConnectionInfo(@Param("start") Integer start, @Param("rows") Integer rows);


    /**
     * 获取连接信息
     * @author Fang Ruichuan
     * @date 2021/10/25 14:38
     * @return int 数据总数
     */
    Long getConnectionInfoTotal();

    /**
     * 根据id删除连接信息
     * @author Fang Ruichuan
     * @date 2021/10/25 15:55
     * @param id 主键id
     * @return int
     */
    int deleteInfoById(@Param("id") Integer id);


    /**
     * 根据id修改时间
     * @author Fang Ruichuan
     * @date 2021/10/26 16:17
     * @param id 主键
     */
    void updateBackupTime(@Param("id") Integer id);

    /**
     * 查询所有的备份任务
     *
     * @return List<HzsrbackupCopyList> 备份任务列表
     * @author xinchao
     * @date 2021/10/29 19:08
     */
    List<DatabaseConfig> findAllTasks();


    /**
     * 根据id修改最大备份大小
     *
     * @param id 主键ID
     * @author Fang Ruichuan
     * @date 2021/11/8 14:46
     */
    void updateBackupLargestSize(@Param("id") Integer id, @Param("largestSize") Long largestSize);

    /**
     * 根据数据库备份任务配置ID，修改定时任务状态
     * @author Fang Ruichuan
     * @date 2021/11/10 15:53
     * @param dbId      数据库备份任务配置ID
     * @param execFlag  状态(1-on, 0-off)
     * @return int
     */
    @Update("update myweb_dbbackup.dbbackup_database_config set exec_flag = #{execFlag} where id = #{dbId};")
    int changeExecFlag(@Param("dbId") Integer dbId, @Param("execFlag") Byte execFlag);
}