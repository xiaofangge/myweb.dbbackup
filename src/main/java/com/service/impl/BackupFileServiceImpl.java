package com.service.impl;

import com.mapper.BackupFileMapper;
import com.pojo.BackupFile;
import com.service.BackupFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("all")
@Service
@Transactional
public class BackupFileServiceImpl implements BackupFileService {

    @Autowired
    private BackupFileMapper fileMapper;

    @Override
    public void insert(BackupFile backupFile) {
        fileMapper.insert(backupFile);
    }

    @Override
    public List<BackupFile> findPageFile(Integer recordId, String tableName, String orderBy, String orderMethod, Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        return fileMapper.findPageFile(recordId, tableName, orderBy, orderMethod, start, rows);
    }

    @Override
    public Long getPageTotal(Integer recordId, String tableName, String orderBy, String orderMethod) {
        return fileMapper.getPageTotal(recordId, tableName, orderBy, orderMethod);
    }

    @Override
    public void deleteByRecord(Integer recordId) {
        fileMapper.deleteByRecord(recordId);
    }
}
