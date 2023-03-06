package com.example.demo.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modules.user.dao.SysUserTokenMapper;
import com.example.demo.modules.user.entity.SysUserToken;
import com.example.demo.modules.user.service.ShiroService;

@Service
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysUserTokenMapper mapper;

    @Override
    public SysUserToken queryByUserId(Long userId) {
        return mapper.queryByUserId(userId);
    }

    @Override
    public SysUserToken queryByToken(String token) {
        return mapper.queryByToken(token);
    }

}