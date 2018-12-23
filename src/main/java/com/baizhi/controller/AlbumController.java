package com.baizhi.controller;

import com.baizhi.dto.AlbumDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("album")
@Log4j
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    //分页查
    @RequestMapping("queryPage")
    @ResponseBody
    public AlbumDto queryPage(Integer page, Integer rows){
        return albumService.queryPage(page,rows);
    }

    //添加专辑
    @ResponseBody
    @RequestMapping("insert")
    public void insert(Album album, @RequestParam("file1") MultipartFile file1, HttpSession session)throws IllegalStateException, IOException {
        albumService.insert(album,file1,session);
    }

    //添加音频 （章节）
    @RequestMapping("insertChapter")
    @ResponseBody
    public void insertChapter(Chapter chapter, @RequestParam("file2") MultipartFile file2, HttpSession session)throws IllegalStateException, IOException{
        albumService.insertChapter(chapter,file2,session);
    }


    //@RequestMapping(value = "/testDownload", method = RequestMethod.GET) 设置请求方式
    //音频下载
    @RequestMapping("download")
    public void download(String url, HttpSession session, HttpServletResponse response)throws Exception{
        System.out.println("请求到达:"+url);
        albumService.download(url,session,response);
    }

    //删除
    @ResponseBody
    @RequestMapping("delete")
    public int delete(Integer id,HttpSession session){
        log.info(id);
        return albumService.delete(id,session);
    }
}
