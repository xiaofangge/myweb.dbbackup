package com.service;


import com.pojo.LoginLog;

import java.util.List;

public interface LoginLogService {

    void insert(LoginLog loginLog);

    List<LoginLog> findLogList(Integer page, Integer limit);

    long logCount();
}
