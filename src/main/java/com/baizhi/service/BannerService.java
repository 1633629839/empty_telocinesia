package com.baizhi.service;

import com.baizhi.dto.BannerDto;
import com.baizhi.entity.Banner;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface BannerService {

    //page:当前第几页
    //rows:每页几行
    public BannerDto queryPage(Integer page, Integer rows);

    public void deleter(Banner banner,HttpSession session);

    public Banner update(Banner banner);

    public void insert(Banner banner,MultipartFile file1, HttpSession session)throws IllegalStateException, IOException;
}
