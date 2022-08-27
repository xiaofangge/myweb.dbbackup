package com.controller;

import com.common.anotation.OperationLog;
import com.common.vo.R;
import com.utils.DriverUtil;
import com.utils.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("all")
@Controller
public class IndexController {

    private final static Logger log = LoggerFactory.getLogger(IndexController.class);

    /**
     * 测试连接数据库是否成功
     * @author Fang Ruichuan
     * @date 2021/10/22 16:59
     * @param hostIp        服务器地址
     * @param databaseName  数据库名称
     * @param username      数据库用户名
     * @param password      连接密码
     * @return Vo 返回连接是否成功消息体
     */
    @OperationLog(message = "连接数据库", operation = OperationType.CONNECTION)
    @ResponseBody
    @RequestMapping(value = "/testConnection", method = RequestMethod.POST)
    public R testMysqlConnection(@RequestParam(value = "hostIp", required = true) String hostIp,
                                 @RequestParam(value = "hostPort", required = true) Integer hostPort,
                                 @RequestParam(value = "databaseName", required = true) String databaseName,
                                 @RequestParam(value = "dbVersion", required = true) Integer dbVersion,
                                 @RequestParam(value = "username", required = true) String username,
                                 @RequestParam(value = "password", required = true) String password) {
        log.info("/testConnection请求进来了");
        log.info("服务器地址hostIp = {}, 端口hostPort = {}, 数据库 = {}, " +
                "数据库用户名username = {}, 密码password = {}", hostIp, hostPort, databaseName, username, password);

        // 封装返回消息
        String url = "jdbc:mysql://" + hostIp + ":" + hostPort + "/" + databaseName +
                "?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=true&useUnicode=true";

        // 声明Connection对象
        Connection con = null;
        try {
            if (dbVersion.equals(1)) {
                Class.forName(DriverUtil.DRIVER_FOR_8);
            } else if (dbVersion.equals(2)) {
                Class.forName(DriverUtil.DRIVER_FOR_5);
            }
            con = DriverManager.getConnection(url, username, password);
            log.info("{} 连接成功", hostIp);
        } catch (Exception e) {
            log.info("{} 连接失败", hostIp);
            e.printStackTrace();
            return R.fail(hostIp + " 连接失败");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return R.success(hostIp + " 连接成功");
    }


}
