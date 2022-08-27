package com.controller;


import com.common.anotation.OperationLog;
import com.common.dto.RuleSave;
import com.common.vo.R;
import com.pojo.Rule;
import com.service.RuleService;
import com.utils.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Controller
@RequestMapping("/rule")
public class RuleController {

    private final static Logger log = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;

    // RestFul风格
    @OperationLog(message = "获取单条备份规则", operation = OperationType.QUERY)
    @GetMapping("/get/{databaseId}")
    @ResponseBody
    public List<Rule> rules(@PathVariable("databaseId") Integer databaseId) {
        return ruleService.getRule(databaseId);
    }

    @OperationLog(message = "保存备份规则", operation = OperationType.ADD)
    @PostMapping("/save")
    @ResponseBody
    public R saveRule(RuleSave ruleSave) {

        Integer databaseId = ruleSave.getDatabaseId();
        Integer backupCycle = ruleSave.getBackupCycle();

        try {
            // 保存之前删除原来的
            ruleService.deleteRule(databaseId);
            saveRule(ruleSave.getSunday(), 1, databaseId, backupCycle);
            saveRule(ruleSave.getMonday(), 2, databaseId, backupCycle);
            saveRule(ruleSave.getTuesday(), 3, databaseId, backupCycle);
            saveRule(ruleSave.getWednesday(), 4, databaseId, backupCycle);
            saveRule(ruleSave.getThursday(), 5, databaseId, backupCycle);
            saveRule(ruleSave.getFriday(), 6, databaseId, backupCycle);
            saveRule(ruleSave.getSaturday(), 7, databaseId, backupCycle);
            return R.success("保存规则成功");
        } catch (RuntimeException e) {
            log.error("系统出错：{}", e.getMessage());
        }
        return R.fail("保存规则失败");
    }

    private void saveRule(ArrayList<String> list, Integer week, Integer databaseId, Integer backupCycle) {
        StringBuilder backupTime = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            Rule rule = new Rule();
            rule.setBackupWeek(week);
            for (String s : list) {
                if (s.equals(list.get(list.size() - 1))) {
                    backupTime.append(s);
                    break;
                }
                backupTime.append(s).append(",");
            }
            log.info("backupTime => {}", backupTime);
            rule.setBackupWholePoint(backupTime.toString());
            rule.setDatabaseId(databaseId);
            rule.setBackupCycle(backupCycle);
            ruleService.saveRule(rule);
        }
    }
}
