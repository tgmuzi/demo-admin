package com.example.demo.modules.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.modules.user.entity.SysUser;
import com.example.demo.modules.user.entity.SysUserToken;
import com.example.demo.utils.AjaxObject;

public interface LoginService {
    SysUser getUserByName(String getMapByName);

    SysUser getMapById(Long userId);

    void save1(SysUserToken token);

    void update1(SysUserToken token);

    SysUserToken queryByUserId(Long userId);

    AjaxObject createToken(HttpServletRequest request, HttpServletResponse response, SysUser user);
}