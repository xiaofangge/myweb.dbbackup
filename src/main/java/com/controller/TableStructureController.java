package com.controller;


import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.TableStructure;
import com.service.TableStructureService;
import com.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@SuppressWarnings("all")
@Controller
@RequestMapping("/structure")
public class TableStructureController {

    @Autowired
    private TableStructureService tableStructureService;


    @OperationLog(message = "查看表结构", operation = OperationType.QUERY)
    @GetMapping("/list")
    @ResponseBody
    public R list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                  @RequestParam(value = "hostIp", required = true) String hostIp,
                  @RequestParam(value = "hostPort", required = true) String hostPort,
                  @RequestParam(value = "databaseName", required = true) String databaseName,
                  @RequestParam(value = "dbVersion", required = true) String dbVersion,
                  @RequestParam(value = "tableName", required = true) String tableName,
                  @RequestParam(value = "username", required = true) String username,
                  @RequestParam(value = "password", required = true) String password) {
        // 删除原来的
        tableStructureService.deleteOld(hostIp, databaseName, tableName);
        // 添加表结构信息
        tableStructureService.saveTableStructure(hostIp, hostPort, databaseName, Integer.parseInt(dbVersion), tableName, username, password);
        List<TableStructure> structureList = tableStructureService.findStructureInfo(page, limit, hostIp, databaseName, tableName);
        Long structureCount = tableStructureService.structureCount(hostIp, databaseName, tableName);
        return R.layuiTable(structureList, structureCount);
    }
}
