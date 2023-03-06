package com.example.demo.modules.user.service.impl;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.modules.user.dao.SysUserMapper;
import com.example.demo.modules.user.entity.SysUser;
import com.example.demo.modules.user.service.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public void save(SysUser user, Long sysUserId) {

        user.setCreateTime(new Date());
        // sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        baseMapper.insert(user);

    }

}