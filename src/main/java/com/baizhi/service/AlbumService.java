package com.baizhi.service;

import com.baizhi.dto.AlbumDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface AlbumService {
    //分页显示
    public AlbumDto queryPage(Integer page, Integer rows);

    //添加专辑
    public void insert(Album album, MultipartFile multipartFile, HttpSession session)throws IllegalStateException, IOException;

    //添加音频 （章节）
    public void insertChapter(Chapter chapter, MultipartFile multipartFile, HttpSession session)throws IllegalStateException, IOException ;

    //删除音频或者专辑
    public int delete(Integer id,HttpSession session);

    //下载音频
    public void download(String url, HttpSession session, HttpServletResponse response) throws Exception;
}
