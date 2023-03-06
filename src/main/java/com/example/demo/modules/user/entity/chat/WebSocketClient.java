package com.example.demo.modules.user.entity.chat;

import lombok.Data;

import javax.websocket.Session;

/**
 * @author Alan Chen
 * @description WebSocket客户端连接
 * @date 2020-04-08
 */
@Data
public class WebSocketClient {

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 连接的uri
    private String uri;

}