package com.example.demo.modules.chat.dao;

import com.example.demo.modules.chat.entity.ChatLog;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author muzi
 * @since 2022-08-30
 */
@Mapper
public interface ChatLogMapper extends BaseMapper<ChatLog> {

}
