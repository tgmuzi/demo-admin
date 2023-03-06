package com.example.demo.modules.user.service;

import com.example.demo.modules.user.entity.SysUser;

public interface SysUserService {

    /**
     * 保存用户
     */
    void save(SysUser user, Long sysUserId);
}