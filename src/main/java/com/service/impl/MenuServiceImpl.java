package com.service.impl;

import com.common.vo.MenuVo;
import com.mapper.MenuMapper;
import com.pojo.Menu;
import com.service.MenuService;
import com.utils.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@SuppressWarnings("all")
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Map<String, Object> menu() {
        Map<String, Object> map = new HashMap<>(16);
        Map<String, Object> home = new HashMap<>(16);
        Map<String, Object> logo = new HashMap<>(16);
        List<Menu> menuList = menuMapper.findAllByStatus((byte) 1);

        List<MenuVo> menuInfo = new ArrayList<>();
        for (Menu menu : menuList) {
            MenuVo menuVo = MenuVo.menuVo(menu.getMenuKey().getId(),
                    menu.getPid(), menu.getMenuKey().getTitle(), menu.getIcon(),
                    menu.getMenuKey().getHref(), menu.getTarget());
            menuInfo.add(menuVo);
        }
        map.put("menuInfo", TreeUtil.toTree(menuInfo, 0L));

        home.put("title", "首页");
        home.put("href", "/welcome");

        logo.put("title", "DB-Backup");
        logo.put("image", "/static/images/logo.gif");
        logo.put("href", "");
        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }
}
