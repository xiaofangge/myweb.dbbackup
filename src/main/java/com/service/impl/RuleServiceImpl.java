package com.service.impl;

import com.mapper.RuleMapper;
import com.pojo.Rule;
import com.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@SuppressWarnings("all")
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public void saveRule(Rule rule) {
        ruleMapper.saveRule(rule);
    }

    @Override
    public void deleteRule(Integer databaseId) {
        ruleMapper.deleteRule(databaseId);
    }

    @Override
    public List<Rule> getRule(Integer databaseId) {
        return ruleMapper.getRule(databaseId);
    }

    @Override
    public Integer getBackupCycle(Integer databaseId) {
        return ruleMapper.getBackupCycle(databaseId);
    }
}
