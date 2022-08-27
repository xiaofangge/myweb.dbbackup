package com.service.impl;

import com.mapper.LoginLogMapper;
import com.pojo.LoginLog;
import com.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@SuppressWarnings("all")
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insert(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public List<LoginLog> findLogList(Integer page, Integer limit) {
        Integer start = (page - 1) * limit;
        return loginLogMapper.findLogList(start, limit);
    }

    @Override
    public long logCount() {
        return loginLogMapper.logCount();
    }
}
