package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service("adminService")
@Log4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public void login(Admin admin, String enCode, HttpSession session) {
        enCode=enCode.toLowerCase();
        log.info("admin收参结果:"+admin+"---验证码"+enCode);
        String code=(String)session.getAttribute("code");
        if(!code.equals(enCode)) throw new RuntimeException("验证码错误");
        Admin admin1=adminMapper.selectByPrimaryKey(admin.getId());
        if(admin1==null) throw  new RuntimeException("请检查账号后再登陆");
        if(!admin1.getPassword().equals(admin.getPassword()))throw  new RuntimeException("密码错误");
        session.setAttribute("userAdmin",admin1);
        log.info("数据库查询结果"+admin1);
    }
}
