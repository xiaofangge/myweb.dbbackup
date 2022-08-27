package com.controller;

import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.ClearLog;
import com.service.ClearLogService;
import com.utils.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@SuppressWarnings("all")
@RequestMapping("/clearlog")
public class ClearLogController {

    @Autowired
    private ClearLogService clearLogService;

    @OperationLog(message = "查看数据库文件清理日志", operation = OperationType.QUERY)
    @GetMapping("/list")
    @ResponseBody
    public R list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ClearLog> clearLogList = clearLogService.clearLogList(page, limit);
        Long clearLogCount = clearLogService.clearLogCount();
        return R.layuiTable(clearLogList, clearLogCount);
    }
}
