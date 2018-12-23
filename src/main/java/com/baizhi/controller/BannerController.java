package com.baizhi.controller;

import com.baizhi.dto.BannerDto;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;


@Controller
@RequestMapping("banner")
@Log4j
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("queryPage")
    @ResponseBody
    public BannerDto queryPage(Integer page, Integer rows){

        return bannerService.queryPage(page,rows);
    }

    @RequestMapping("update")
    @ResponseBody
    public Banner upadte( Banner banner){

        return bannerService.update(banner);
    }

    @RequestMapping("delete")
    @ResponseBody
    public void delete(Banner banner,HttpSession session){

        bannerService.deleter(banner,session);
    }

    @RequestMapping("insert")
    @ResponseBody
    public void insert(Banner banner, @RequestParam("file1") MultipartFile file1, HttpSession session)throws IllegalStateException, IOException {
        bannerService.insert(banner,file1,session);
    }
}
