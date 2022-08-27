package com.service;


import com.pojo.BackupFile;

import java.util.List;

public interface BackupFileService {
    void insert(BackupFile backupFile);

    List<BackupFile> findPageFile(Integer recordId, String tableName, String orderBy, String orderMethod, Integer page, Integer rows);

    Long getPageTotal(Integer recordId, String tableName, String orderBy, String orderMethod);

    void deleteByRecord(Integer recordId);
}
