package com.example.demo.modules.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.example.demo.modules.chat.controller.WebSocketServer;
import com.example.demo.modules.chat.service.IChatLogService;

/**
 * @author Alan Chen
 * @description 开启WebSocket支持
 * @date 2020-04-08
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    private void setIChatLogService(IChatLogService chatLogService) {
        WebSocketServer.chatLogService = chatLogService;
    }
}