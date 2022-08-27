package com.mapper;


import com.pojo.Rule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RuleMapper {

    // 保存备份规则
    void saveRule(Rule rule);

    // 根据数据库连接ID删除规则
    void deleteRule(@Param("databaseId") Integer databaseId);

    // 获取备份规则
    List<Rule> getRule(@Param("databaseId") Integer databaseId);

    Integer getBackupCycle(Integer databaseId);
}
