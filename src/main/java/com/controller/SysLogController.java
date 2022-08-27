package com.controller;

import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.SysLog;
import com.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 川川
 * @version 1.0
 * @description: TODO
 * @date 2021/12/5 14:31
 */
@Controller
@SuppressWarnings("all")
@RequestMapping("/syslog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/list")
    @ResponseBody
    public R logList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                     @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<SysLog> sysLogList = sysLogService.sysLogList(page, limit);
        Long sysLogCount = sysLogService.sysLogCount();
        return R.layuiTable(sysLogList, sysLogCount);
    }
}
