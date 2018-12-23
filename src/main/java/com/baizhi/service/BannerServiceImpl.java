package com.baizhi.service;

import com.baizhi.dto.BannerDto;
import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("bannerService")
@Log4j
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    //page:当前第几页
    //rows:每页几行
    public BannerDto queryPage(Integer page, Integer rows){
        BannerDto bannerDto=new BannerDto();
        //起始下标 offset
        //每页显示的行数 limit
        log.info(" page:当前第几页 "+page+"----rows:每页几行"+rows);
        RowBounds rowBounds=new RowBounds((page-1)*rows,rows);
        //RowBounds rowBounds=new RowBounds(1,2);  //查询的起始下标,起始下标从0开始，查几条
        List<Banner> list=bannerMapper.selectByRowBounds(null,rowBounds);
        log.info("查到的内容");
        for (Banner banner : list) {
            log.info(banner);
        }
        bannerDto.setRows(list);
        bannerDto.setTotal(bannerMapper.selectCount(null));

       return  bannerDto;
        //return list;
    }

    public void deleter(Banner banner,HttpSession session){
        log.info("delete请求参数 Banner:"+banner);
        Banner banner1=bannerMapper.selectByPrimaryKey(banner);
        log.info("查一个Banner"+banner1);
        String realPath=session.getServletContext().getRealPath("/img/banner");
        File file=new File(realPath+banner1.getImgPath());
        file.delete();
        log.info("删除图片");
        bannerMapper.delete(banner);
        log.info("删除数据");
    }

    public Banner update(Banner banner){
        log.info(banner);
        bannerMapper.updateByPrimaryKey(banner);
        return bannerMapper.selectByPrimaryKey(banner);
    }

    public void insert(Banner banner, MultipartFile multipartFile, HttpSession session)throws IllegalStateException,IOException{
        banner.setStatus(false);

        Date time=new Date();
        banner.setPubDate(time);

        Long longTmie=time.getTime();

        String realPath=multipartFile.getOriginalFilename();
        realPath="/"+longTmie%10000+realPath;
        log.info("Banner ingPath属性中保存的:"+realPath);
        banner.setImgPath(realPath);

        int i=bannerMapper.insert(banner);
        log.info("添加成功:"+i);

        String realPath1=session.getServletContext().getRealPath("/img/banner");
        File file1=new File(realPath1+realPath);
        log.info("session.getServletContext().getRealPath()的地址是:"+realPath1);
        multipartFile.transferTo(file1);
        log.info("图片保存到服务器成功");

        /*File file2 =new File("D:/ideaIU-2018.3/resource-idea/empty_telocinesia/background-demo/src/main/webapp/img/banner"+realPath);
        multipartFile.transferTo(file2);
        log.info("文件保存到本地");*/

    }
}
