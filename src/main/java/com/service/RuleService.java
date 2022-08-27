package com.service;



import com.pojo.Rule;

import java.util.List;

public interface RuleService {
    void saveRule(Rule rule);

    void deleteRule(Integer databaseId);

    List<Rule> getRule(Integer databaseId);

    Integer getBackupCycle(Integer databaseId);
}
