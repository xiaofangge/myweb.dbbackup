package com.controller;


import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.LoginLog;
import com.service.LoginLogService;
import com.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SuppressWarnings("all")
@Controller
@RequestMapping("/log")
public class LoginLogController {

    @Autowired
    LoginLogService loginLogService;

    @OperationLog(message = "查看登录日志", operation = OperationType.QUERY)
    @RequestMapping("/list")
    @ResponseBody
    public R list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit) {
        List<LoginLog> logList = loginLogService.findLogList(page, limit);
        long count = loginLogService.logCount();
        return R.layuiTable(logList, count);
    }
}
