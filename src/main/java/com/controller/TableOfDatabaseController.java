package com.controller;


import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.TablesOfDatabase;
import com.service.TableOfDatabaseService;
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
@RequestMapping("/table")
public class TableOfDatabaseController {

    @Autowired
    private TableOfDatabaseService tableOfDatabaseService;

    @OperationLog(message = "查看数据表", operation = OperationType.QUERY)
    @GetMapping("/list")
    @ResponseBody
    public R list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                  @RequestParam(value = "databaseName", required = true) String databaseName,
                  @RequestParam(value = "dbVersion", required = true) Integer dbVersion,
                  @RequestParam(value = "hostIp", required = true) String hostIp,
                  @RequestParam(value = "hostPort", defaultValue = "3306") Integer hostPort,
                  @RequestParam(value = "username", required = true) String username,
                  @RequestParam(value = "password", required = true) String password) {

        tableOfDatabaseService.deleteBydAndh(databaseName, hostIp);
        tableOfDatabaseService.saveTableNameAndTableDesc(hostIp, hostPort, databaseName, dbVersion, username, password);
        List<TablesOfDatabase> tables = tableOfDatabaseService.findAllTables(page, limit, databaseName, hostIp);
        Long count = tableOfDatabaseService.getCount(databaseName, hostIp);
        return R.layuiTable(tables, count);
    }
}
