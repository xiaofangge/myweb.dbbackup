package com.service.impl;


import com.mapper.DatabaseConfigMapper;
import com.pojo.DatabaseConfig;
import com.service.DatabaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SuppressWarnings("all")
@Service
@Transactional
public class DatabaseConfigServiceImpl implements DatabaseConfigService {

    @Autowired
    private DatabaseConfigMapper databaseConfigMapper;


    @Override
    public DatabaseConfig select(Integer id) {
        return databaseConfigMapper.select(id);
    }

    @Override
    public int changeExecFlag(Integer dbId, Integer exec) {
        // 先将Integer类型转换为byte类型
        byte execFlag = (byte) exec.intValue();
        return databaseConfigMapper.changeExecFlag(dbId, Byte.valueOf(execFlag));
    }

    @Override
    public int insert(DatabaseConfig record) {
        return databaseConfigMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DatabaseConfig record) {
        return databaseConfigMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<DatabaseConfig> findConnectionInfo(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        return databaseConfigMapper.findConnectionInfo(start, rows);
    }

    @Override
    public Long getConnectionInfoTotal() {
        return databaseConfigMapper.getConnectionInfoTotal();
    }

    @Override
    public int deleteInfoById(Integer id) {
        return databaseConfigMapper.deleteInfoById(id);
    }

    @Override
    public void updateBackupTime(Integer id) {
        databaseConfigMapper.updateBackupTime(id);
    }

    @Override
    public List<DatabaseConfig> findAllTasks() {
        return databaseConfigMapper.findAllTasks();
    }
}
