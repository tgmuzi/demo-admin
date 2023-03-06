package com.example.demo.modules.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.modules.user.entity.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser queryByUserId(String userName);
}