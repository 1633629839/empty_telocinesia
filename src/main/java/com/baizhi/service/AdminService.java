package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;

public interface AdminService {
    public void login(Admin admin, String enCode, HttpSession session);
}
