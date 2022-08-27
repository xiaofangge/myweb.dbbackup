package com.service.impl;


import com.mapper.ClearLogMapper;
import com.pojo.ClearLog;
import com.service.ClearLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@SuppressWarnings("all")
public class ClearLogServiceImpl implements ClearLogService {

    @Autowired
    private ClearLogMapper clearLogMapper;

    @Override
    public void save(ClearLog clearLog) {
        clearLogMapper.save(clearLog);
    }

    @Override
    public List<ClearLog> clearLogList(Integer page, Integer limit) {
        return clearLogMapper.clearLogList((page-1)*limit, limit);
    }

    @Override
    public Long clearLogCount() {
        return clearLogMapper.clearLogCount();
    }
}
