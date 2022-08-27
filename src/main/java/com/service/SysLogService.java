package com.service;

import com.pojo.SysLog;

import java.util.List;

public interface SysLogService {
    List<SysLog> sysLogList(Integer page, Integer limit);

    Long sysLogCount();
}
