package com.controller;

import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.pojo.BackupRecord;
import com.service.BackupRecordService;
import com.utils.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@SuppressWarnings("all")
@Controller
@RequestMapping("/record")
public class BackupRecordController {

    private final static Logger log = LoggerFactory.getLogger(BackupRecordController.class);

    @Autowired
    private BackupRecordService backupRecordService;


    @OperationLog(message = "查看数据库备份记录", operation = OperationType.QUERY)
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R findAll(@RequestParam(value = "page", required = true) Integer page,
                     @RequestParam(value = "limit", required = true) Integer limit,
                     @RequestParam(value = "database", required = false) String database,
                     @RequestParam(value = "backupRes", required = false) String res) {
        log.info("/record/list请求进来了");
        log.info("page = {}, limit = {}", page, limit);
        List<BackupRecord> allRecord = backupRecordService.findAllRecord(page, limit, database, res);
        Long recordTotal = backupRecordService.getRecordTotal(database, res);
        return R.layuiTable(allRecord, recordTotal);
    }

    @RequestMapping("/database")
    @ResponseBody
    public List<BackupRecord> findDatabase() {
        log.info("/record/database请求进来了");
        List<BackupRecord> databaseList = backupRecordService.findDatabase();
        return databaseList;
    }
}
