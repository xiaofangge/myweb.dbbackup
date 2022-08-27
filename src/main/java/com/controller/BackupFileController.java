package com.controller;


import com.common.vo.R;
import com.pojo.BackupFile;
import com.service.BackupFileService;
import com.utils.FileUtil;
import com.utils.WindowsCmdUtils;
import com.utils.OperationType;
import com.utils.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.common.anotation.OperationLog;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@SuppressWarnings("all")
@Controller
@RequestMapping("/backupFile")
public class BackupFileController {

    private final static Logger logger = LoggerFactory.getLogger(BackupFileController.class);


    @Autowired
    private BackupFileService backupFileService;

    @OperationLog(message = "查看数据库备份文件", operation = OperationType.QUERY)
    @PostMapping("/list")
    @ResponseBody
    public R list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                  @RequestParam(value = "recordId", required = true) String recordId,
                  @RequestParam(value = "tableName", required = false) String tableName,
                  @RequestParam(value = "orderBy", required = false) String orderBy,
                  @RequestParam(value = "orderMethod", required = false) String orderMethod) {
        logger.info("page = {}, limit = {}, recordId = {}, tableName = {}, orderBy = {}, orderMethod = {}",
                page, limit, recordId, tableName, orderBy, orderMethod);
        List<BackupFile> backupFileList = backupFileService.findPageFile(Integer.parseInt(recordId), tableName, orderBy, orderMethod, page, limit);
        Long fileCount = backupFileService.getPageTotal(Integer.parseInt(recordId), tableName, orderBy, orderMethod);
        return R.layuiTable(backupFileList, fileCount);
    }


    @OperationLog(message = "下载单个数据库备份文件", operation = OperationType.DOWN_LOAD)
    @GetMapping("/download")
    public void download(HttpServletResponse response, @RequestParam("totalFileName") String totalFileName) {
        logger.info("/file/download/ 请求进来了");
        logger.info("totalFileName = {}", totalFileName);

        File file = new File(totalFileName);
        if (!file.exists()) {
            logger.info("文件不存在");
            return;
        }
        FileUtil.down(response, totalFileName);
        logger.info("下载成功");
    }

    /**
     * 实现压缩之后并下载文件
     * @param response
     * @param totalFilePath
     * @author Fang Ruichuan
     * @date 2021/11/8 17:36
     */
    @OperationLog(message = "下载一条备份记录中的所有文件", operation = OperationType.DOWN_LOAD)
    @GetMapping("downloadAll")
    public void downloadAll(HttpServletResponse response, @RequestParam("totalFilePath") String totalFilePath, @RequestParam("backupDatabase") String backupDatabase) throws FileNotFoundException {
        logger.info("/file/downloadAll/ 请求进来了");
        logger.info("totalFileName = {}", totalFilePath);
        File file = new File(totalFilePath);
        if (!file.exists()) {
            logger.info("文件不存在");
            return;
        }
        String createZip = totalFilePath + "-" + backupDatabase + ".zip";

        // 压缩文件夹
        FileOutputStream fos = new FileOutputStream(new File(createZip));
        ZipUtils.toZip(totalFilePath, fos, false);

        FileUtil.down(response, createZip);
        logger.info("下载成功");
        // 下载成功之后删除生成的压缩包
        StringBuilder command = new StringBuilder().append("del").append(" ")
                        .append(createZip);

        logger.info("删除压缩包命令：{}", command.toString());
        try {
            WindowsCmdUtils.executeLinuxCmd(command.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("删除压缩文件成功");
    }
}
