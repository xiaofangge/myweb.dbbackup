package com.controller;

import com.pojo.Rousel;
import com.service.RouselService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 川川
 * @version 1.0
 * @description: TODO
 * @date 2021/12/8 10:22
 */
@Controller
@RequestMapping("/rousel")
public class RouselController {

    @Resource
    private RouselService rouselService;

    @GetMapping("/list")
    @ResponseBody
    public List<Rousel> list() {
        return rouselService.list();
    }
}
