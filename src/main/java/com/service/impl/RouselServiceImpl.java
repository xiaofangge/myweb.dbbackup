package com.service.impl;

import com.mapper.RouselMapper;
import com.pojo.Rousel;
import com.service.RouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 川川
 * @version 1.0
 * @description: TODO
 * @date 2021/12/8 10:23
 */
@Service
public class RouselServiceImpl implements RouselService {

    @Resource
    private RouselMapper rouselMapper;

    @Override
    public List<Rousel> list() {
        return rouselMapper.list();
    }
}
