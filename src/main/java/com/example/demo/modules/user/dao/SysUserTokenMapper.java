package com.example.demo.modules.user.dao;

import com.example.demo.modules.user.entity.SysUserToken;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 */
@Mapper
public interface SysUserTokenMapper extends BaseMapper<SysUserToken> {

    SysUserToken queryByUserId(Long userId);

    SysUserToken queryByToken(String token);

}