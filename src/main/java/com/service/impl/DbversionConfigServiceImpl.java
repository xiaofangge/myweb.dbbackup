package com.service.impl;

import com.mapper.DbversionConfigMapper;
import com.pojo.DbversionConfig;
import com.service.DbversionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@SuppressWarnings("all")
public class DbversionConfigServiceImpl implements DbversionConfigService {

    @Autowired
    private DbversionConfigMapper dbversionConfigMapper;

    @Override
    public List<DbversionConfig> dbversionList() {
        return dbversionConfigMapper.dbversionList();
    }
}
