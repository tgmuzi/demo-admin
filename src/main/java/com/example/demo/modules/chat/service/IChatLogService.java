package com.example.demo.modules.chat.service;

import com.example.demo.modules.chat.entity.ChatLog;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author muzi
 * @since 2022-08-30
 */
public interface IChatLogService extends IService<ChatLog> {

    int save(ChatLog chatLog);

}
