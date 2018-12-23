package com.baizhi.controller;

import com.baizhi.conf.MakeCertPic;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
@Log4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MakeCertPic makeCertPic;

    @RequestMapping("login")
    public String login(Admin admin, String enCode, HttpSession session){

        try {
            adminService.login(admin,enCode,session);
            return "redirect:/main/main.jsp";
        }catch (RuntimeException e){
            log.error("登陆失败"+e.getMessage());
           session.setAttribute("loginException",e.getMessage());

            //return "forward:/login.jsp";
            return "redirect:/login.jsp";
        }
    }

    @RequestMapping("code")
    public void code(HttpServletResponse response,HttpSession session) throws IOException{
        String code=makeCertPic.getCertPic(80,25,response.getOutputStream());
        session.setAttribute("code",code);
        log.info("验证码获取成功:"+code);
    }
}
