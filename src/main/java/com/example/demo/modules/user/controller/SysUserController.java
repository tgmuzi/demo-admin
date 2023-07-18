package com.example.demo.modules.user.controller;

import com.example.demo.modules.AbstractController;
import com.example.demo.service.modules.user.service.SysUserService;
import com.example.demo.utils.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.modules.user.entity.SysUser;
import com.example.demo.utils.AjaxObject;

import cn.hutool.core.lang.Console;

@Controller
@RequestMapping("${adminPath}/sysUser")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存用户
     */
    @RequestMapping("/save")
    @ResponseBody
    public AjaxObject save(@RequestBody SysUser user) {
        String username = user.getUserName();
        user.setUserName(username.trim());

        EntityWrapper<SysUser> ew = new EntityWrapper<SysUser>();
        ew.where("USER_NAME={0}", user.getUserName());
        // SysUser sysUser = sysUserService.selectOne(ew);

        String secretKey = GoogleAuthenticator.genSecret(user.getUserName(), user.getUserName());
        user.setGaSecret(secretKey);
        user.setGaSecretImg(GoogleAuthenticator.getQRBarcodeURL(user.getUserName(), user.getUserName(), secretKey));

        user.setCreateUserId(1L);

        sysUserService.save(user, 1L);

        return AjaxObject.ok().data("新增管理员成功");
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    @ResponseBody
    public AjaxObject info() {
        Console.log(getUser());
        return AjaxObject.ok().put("user", getUser());
    }
}