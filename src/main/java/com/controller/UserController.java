package com.controller;

import com.common.anotation.OperationLog;
import com.common.dto.ModifyPwdDto;
import com.common.vo.R;
import com.service.UserService;
import com.utils.OperationType;
import com.utils.SessionValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@SuppressWarnings("all")
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseBody
    public R register(String captcha, String username, String password, HttpServletRequest request) {
        return userService.register(captcha, username, password, request);
    }


    @PostMapping("/login")
    @ResponseBody
    public R login(String captcha, String username, String password, HttpServletRequest request) {
        logger.info("/user/login请求进来了");
        logger.info("captcha = {}, username = {}, password = {}", captcha, username, password);
        return userService.login(captcha, username, password, request);
    }

    @OperationLog(message = "用户退出", operation = OperationType.LOGOUT)
    @GetMapping("/logout")
    @ResponseBody
    public R logout(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionValues.LOGIN_ADMIN);
        return R.success("退出成功");
    }

    @OperationLog(message = "用户修改密码", operation = OperationType.UPDATE)
    @PostMapping("/modifyPwd")
    @ResponseBody
    public R modifyPwd(@RequestBody ModifyPwdDto pwdDto, HttpServletRequest request) {
        return userService.modifyPwd(pwdDto, request);
    }
}
