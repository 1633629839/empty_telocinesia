package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.entity.Menu;
import com.baizhi.service.MenuService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j
@Controller
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<Menu> queryAll(){
        log.info("请求到达MenuController--queryAll");
        return menuService.queryAll();
    }
}
