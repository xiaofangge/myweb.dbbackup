package com.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 下载
     * 由于HTTP头部的默认编码为ISO-8859-1而我们上传文件于下载文件过程中，提取到的文件名都要通过HTTP头部。
     * 所以我们需要在上传的时候对提取到的文件名进行转码为UTF-8，然后在下载时我们也要进行反向转码为ISO-8859-1.
     * @param response 封装HTTP响应消息
     * @param pathAddress 文件路径
     */
    public static void down(HttpServletResponse response, String pathAddress) {
        File file=new File(pathAddress);
        String fileName = file.getName();

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

        byte[] buff = new byte[1024];
        FileInputStream bis = null;
        OutputStream os;
        try {
            os = response.getOutputStream();
            bis = new FileInputStream(file);
            int readTmp;
            while ((readTmp = bis.read(buff)) != -1) {
                //并不是每次都能读到1024个字节，所有用readTmp作为每次读取数据	的长度，否则会出现文件损坏的错误
                os.write(buff, 0, readTmp);

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (bis != null) try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("success");
    }


    /**
     * 清空整个目录
     * @param f File
     * @author Fang Ruichuan
     * @date 2021/11/3 11:24
     */
    public static void deleteDirectory(File f) {
        if (!f.exists()) {
            return;
        }
        if (f.isFile()) {
            try {
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File[] files = f.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                deleteDirectory(file);
            }
        }
    }

}
