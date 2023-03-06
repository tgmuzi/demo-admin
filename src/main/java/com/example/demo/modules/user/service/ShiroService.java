package com.example.demo.modules.user.service;

import com.example.demo.modules.user.entity.SysUserToken;

public interface ShiroService {

    SysUserToken queryByUserId(Long userId);

    SysUserToken queryByToken(String token);
}