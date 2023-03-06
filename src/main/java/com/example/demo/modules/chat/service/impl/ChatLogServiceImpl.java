package com.example.demo.modules.chat.service.impl;

import com.example.demo.modules.chat.entity.ChatLog;
import com.example.demo.modules.chat.dao.ChatLogMapper;
import com.example.demo.modules.chat.service.IChatLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author muzi
 * @since 2022-08-30
 */
@Service
public class ChatLogServiceImpl extends ServiceImpl<ChatLogMapper, ChatLog> implements IChatLogService {

    @Autowired
    private ChatLogMapper chatLogMapper;

    @Override
    public int save(ChatLog chatLog) {
        System.out.println(chatLog.getFromUser());
        return chatLogMapper.insert(chatLog);
    }

}
