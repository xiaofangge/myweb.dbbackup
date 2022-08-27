package com.service.impl;

import com.mapper.SysLogMapper;
import com.pojo.SysLog;
import com.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 川川
 * @version 1.0
 * @description: TODO
 * @date 2021/12/5 14:35
 */
@Service
@Transactional
@SuppressWarnings("all")
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public List<SysLog> sysLogList(Integer page, Integer limit) {
        return sysLogMapper.sysLogList((page - 1) * limit, limit);
    }

    @Override
    public Long sysLogCount() {
        return sysLogMapper.sysLogCount();
    }
}
