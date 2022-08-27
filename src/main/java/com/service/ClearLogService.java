package com.service;


import com.pojo.ClearLog;

import java.util.List;

public interface ClearLogService {
    void save(ClearLog clearLog);

    List<ClearLog> clearLogList(Integer page, Integer limit);

    Long clearLogCount();
}
