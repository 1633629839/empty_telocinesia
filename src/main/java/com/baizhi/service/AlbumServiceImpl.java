package com.baizhi.service;

import com.baizhi.dto.AlbumDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Album的service实现类
 */
@Service("albumService")
@Log4j
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private ChapterMapper chapterMapper;

    /**
     * 分页查询
     */
    public AlbumDto queryPage(Integer page, Integer rows) {
        log.info("第" + page + "也，每页" + rows + "行");
        List<Album> list = albumMapper.queryAll((page - 1) * rows, rows);
        for (Album album : list) {
            log.info(list);
        }
        rows = albumMapper.selectCount(null);
        log.info("一共:" + rows + "条数据");

        return new AlbumDto(rows, list);
    }

    //添加专辑
    public void insert(Album album, MultipartFile multipartFile, HttpSession session) throws IllegalStateException, IOException {
        log.info("专辑添加业务的Album 请求参数" + album);

        album.setCount(0);

        String realPath = multipartFile.getOriginalFilename();//获取文件名
        Date date = new Date();
        realPath = "/" + date.getTime() % 10000 + realPath;//给文件名加时间戳的后4位
        album.setCoverImg(realPath);//把文件名存到album对象里
        realPath = session.getServletContext().getRealPath("/img/album") + realPath;//设置文件的绝对路径
        File file = new File(realPath);//创建文件对象
        multipartFile.transferTo(file);//把照片保存到文件里
        log.info("图片保存成功，保存地址是:" + realPath);

        int n = albumMapper.insert(album);//把album 存入数据库
        log.info("保存成功 成功" + n + "条 成功后的的Album" + album);

    }

    //添加音频 （章节）
    public void insertChapter(Chapter chapter, MultipartFile multipartFile, HttpSession session) throws IllegalStateException, IOException {
        log.info("添加音频 请求参数chapter:" + chapter);
        Date date = new Date();
        chapter.setUploadDate(date);

        String url = "/" + date.getTime() % 10000 + multipartFile.getOriginalFilename();
        chapter.setUrl(url);

        int i = chapterMapper.insert(chapter);
        log.info("数据库添加成功 成功添加" + i + "条 Chapter:" + chapter);

        String realPath = session.getServletContext().getRealPath("/img/album");
        File file = new File(realPath + url);
        multipartFile.transferTo(file);
        log.info("音频保存成功:" + realPath + url);
    }

    //删除音频或者专辑
    public int delete(Integer id, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img/album");
        log.info("realPath:" + realPath);
        /*
        log.warn("测试ServletContext的getRealPath(\"/img/album\")方法与ServletContext().getContextPath()方法区别");
        log.info("contextPath:"+session.getServletContext().getContextPath());
        RealPath("/img/album"):得到的是绝对路径 D:\ideaIU-2018.3\resource-idea\empty_telocinesia\background-demo\src\main\webapp\img\album
        contextPath:得到是应用名 cmfw
        */
        int count = 0;
        if (id < 10000) {

            Album album = albumMapper.selectByPrimaryKey(id);
            log.info("albumMapper.selectOne(album)" + album);
            /*
            log.warn("selectOne(album)方法与albumMapper.selectByPrimaryKey(id)方法区别");
            log.info("albumMapper.selectByPrimaryKey(id)"+albumMapper.selectByPrimaryKey(id));
            selectOne(album):查一个对象
            selectByPrimaryKey(id):把传入的 值当作主键查一个

            */

            Chapter chapter = new Chapter();
            chapter.setIdAlbum(album.getId());
            List<Chapter> chapters = chapterMapper.select(chapter);
            for (Chapter chapter1 : chapters) {
                log.info("集合循环 chapter1" + chapter);

                File file = new File(realPath + chapter1.getUrl());
                file.delete();
                log.info("音频删除成功");

            }
            count = chapterMapper.delete(chapter);//删除所有符合条件的
            log.info("删除：" + count + "条");

            File file = new File(realPath + album.getCoverImg());
            file.delete();
            log.info("专辑图片删除成功");
            int i = albumMapper.delete(album);
            log.info("删除专辑" + i + "个");

        } else {
            Chapter selectOne = chapterMapper.selectByPrimaryKey(id);
            log.info("删除一个 Chapter" + selectOne);

            File file = new File(realPath + selectOne.getUrl());
            file.delete();
            log.info("删除音频成功");

            count = chapterMapper.delete(selectOne);
        }
        log.info("删除" + count + "成功");
        return count;
    }

    //下载音频
    public void download(String url, HttpSession session, HttpServletResponse response) throws Exception {
        String realPath=session.getServletContext().getRealPath("/img/album");
        File file=new File(realPath+url);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + url);

        byte[] bytes = new byte[1024];

        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
        OutputStream outputStream=response.getOutputStream();
        int i=bufferedInputStream.read(bytes);
        while(i!=-1){
            outputStream.write(bytes,0,bytes.length);
            outputStream.flush();
            i=bufferedInputStream.read(bytes);
        }
        System.out.println("下载完成");


       // byte[] bs=


         /*

        获取server端文件的 字节数组
		String realPath = session.getServletContext().getRealPath("/upload");
		File srcFile = new File(realPath+"/"+fname);
		byte[] bs = FileUtils.readFileToByteArray(srcFile);

		// 设置响应头信息，以附件的形式下载
		response.setHeader("content-disposition", "attchment;filename="+URLEncoder.encode(fname, "utf-8"));

		// 使用响应输出流，往client输出文件内容
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bs);
        */

         /*

         String fileName = "upload.jpg";
    res.setHeader("content-type", "application/octet-stream");设置响应头信息
    res.setContentType("application/octet-stream");
    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);//响应头 文件名
    byte[] buff = new byte[1024];
    BufferedInputStream bis = null;
    OutputStream os = null;
    try {
      os = res.getOutputStream();
      bis = new BufferedInputStream(new FileInputStream(new File("d://"
          + fileName)));
      int i = bis.read(buff);
      while (i != -1) {
        os.write(buff, 0, buff.length);
        os.flush();
        i = bis.read(buff);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bis != null) {
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("success");
          */

         /*
         public void getDownload(Long id, HttpServletRequest request, HttpServletResponse response) {

        // Get your file stream from wherever.
        String fullPath = "E:/" + id +".rmvb";
        File downloadFile = new File(fullPath);

        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
            System.out.println("context getMimeType is null");
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            InputStream myStream = new FileInputStream(fullPath);
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
          */
    }
}