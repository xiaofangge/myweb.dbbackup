package com.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.common.dto.ModifyPwdDto;
import com.mapper.LoginLogMapper;
import com.pojo.LoginLog;
import com.utils.SessionValues;
import com.common.vo.R;
import com.mapper.UserMapper;
import com.pojo.User;
import com.service.UserService;
import com.utils.UserAgentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

import static com.controller.UserController.passwordEncoder;

@SuppressWarnings("all")
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Transactional
    @Override
    public R register(String captcha, String username, String password, HttpServletRequest request) {
        logger.info("captcha = {}", captcha);
        String sessionCode = (String) request.getSession().getAttribute(SessionValues.CAPTCHA);
        logger.info("sessionCode = {}", sessionCode);
        if (sessionCode == null || !StringUtils.equals(sessionCode, captcha)) {
            request.getSession().removeAttribute(SessionValues.CAPTCHA);
            return R.fail("验证码输入错误");
        }
        User userDB = userMapper.queryUserByName(username);
        if (Objects.nonNull(userDB)) {
            return R.fail("该账号已存在，请重新注册");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userMapper.save(user);
        return R.success("恭喜你，账号注册成功");
    }

    @Transactional
    @Override
    public R login(String captcha, String username, String password, HttpServletRequest request) {
        String sessionCaptcha = (String) request.getSession().getAttribute(SessionValues.CAPTCHA);

        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginTime(new Date());
        logger.info("登录IP为 => {}", UserAgentUtil.getIpAddress(request));
        loginLog.setLoginIp(UserAgentUtil.getIpAddress(request));
        loginLog.setBrowserInfo(UserAgentUtil.browserName(request));

        if (sessionCaptcha == null || !StringUtils.equals(sessionCaptcha, captcha)) {
            request.getSession().removeAttribute(SessionValues.CAPTCHA);
            loginLog.setLoginState((byte) 0);
            loginLogMapper.insert(loginLog);
            return R.fail("验证码输入错误");
        }
        User userDB = userMapper.queryUserByName(username);
        if (Objects.isNull(userDB)) {
            loginLog.setLoginState((byte) 0);
            loginLogMapper.insert(loginLog);
            return R.fail("账号不存在，请先注册");
        }
        if (!passwordEncoder.matches(password, userDB.getPassword())) {
            loginLog.setLoginState((byte) 0);
            loginLogMapper.insert(loginLog);
            return R.fail("用户名或密码输入错误");
        }
        loginLog.setLoginState((byte) 1);
        loginLogMapper.insert(loginLog);
        userDB.setPassword(null);
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(30*60);
        session.setAttribute(SessionValues.LOGIN_ADMIN, userDB);
        return R.success("登录成功");
    }

    @Transactional
    @Override
    public R modifyPwd(ModifyPwdDto pwdDto, HttpServletRequest request) {
        User userDB = userMapper.queryById(pwdDto.getUserId());
        if (userDB == null ) {
            return R.fail("该用户已被删除，或session已过期");
        }
        if (!passwordEncoder.matches(pwdDto.getOldPassword(), userDB.getPassword())) {
            return R.fail("原密码输入错误");
        }
        userMapper.updatePwd(userDB.getId(), passwordEncoder.encode(pwdDto.getNewPassword()));
        request.getSession().removeAttribute(SessionValues.LOGIN_ADMIN);
        return R.success("密码修改成功，点击确定跳转到登录页重新登录");
    }
}
