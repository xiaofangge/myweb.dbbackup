package com.service;

import com.common.dto.ModifyPwdDto;
import com.common.vo.R;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    R register(String captcha, String username, String password, HttpServletRequest request);

    R login(String captcha, String username, String password, HttpServletRequest request);

    R modifyPwd(ModifyPwdDto pwdDto, HttpServletRequest request);
}
