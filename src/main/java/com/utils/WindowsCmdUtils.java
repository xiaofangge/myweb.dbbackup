package com.utils;


import com.common.vo.WindowsCmdResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class WindowsCmdUtils {

    private static final Logger logger = LoggerFactory.getLogger(WindowsCmdUtils.class);


    public static WindowsCmdResult executeLinuxCmd(String cmd) throws Exception {

        WindowsCmdResult cmdResult = new WindowsCmdResult();

        Runtime run = Runtime.getRuntime();
        String[] cmdArr = new String[]{"cmd", "/c", cmd};
        Process process = run.exec(cmdArr);

        logger.debug("process = {}", process);

        // 获取进程的标准输入流
        final InputStream is1 = process.getInputStream();
        // 获取进程的标准错误流
        final InputStream is2 = process.getErrorStream();

        AtomicReference<String> errMsg = new AtomicReference<>(null);
        // 启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
        new Thread(() -> {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
            String line1 = null;
            try {
                while ((line1 = br1.readLine()) != null) {
                }
            } catch (IOException e) {
                logger.error("读取标准输入流错误, {}", e.getMessage());
                errMsg.set(Objects.requireNonNull(e.getMessage()));
            } finally {
                try {
                    is1.close();
                } catch (IOException e) {
                    logger.error("关闭windows-cmd进程标准输入流异常, {}", e.getMessage());
                }
            }
        }, "读标准输出流").start();

        new Thread(() -> {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
            String line2 = null;
            try {
                while ((line2 = br2.readLine()) != null);
            } catch (IOException e) {
                logger.error("读取标准错误流错误, {}", e.getMessage());
                errMsg.set(Objects.requireNonNull(e.getMessage()));
            } finally {
                try {
                    is2.close();
                } catch (IOException e) {
                    logger.error("关闭windows-cmd进程标准错误流异常, {}", e.getMessage());
                }
            }
        }, "读标准错误流").start();


        int exeResult = process.waitFor();
        logger.info("process.waitFor() = {}", exeResult);
        if (exeResult == 0) {
            logger.info("命令执行成功");
            cmdResult.setFlag(true);
            cmdResult.setMsg("process.waitFor() = " + exeResult);
            return cmdResult;
        }
        cmdResult.setFlag(false);
        cmdResult.setMsg("命令执行失败, process.waitFor() = " + exeResult);
        return cmdResult;
    }
}

