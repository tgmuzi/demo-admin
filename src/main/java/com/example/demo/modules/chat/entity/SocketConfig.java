package com.example.demo.modules.chat.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author PengPan
 * @version 1.0
 * @date 2020/7/16 15:41
 */
@Data
public class SocketConfig {
    // 聊天类型 0：公告 1：聊天室 2：通知 3：内部邮件 4：特殊推送
    private int type;
    // 发送者
    private String fromUser;
    // 发送者昵称
    private String nickName;
    // 接受者
    private String toUser;
    // 消息
    private String msg;
    // 消息类型 1：文本 2：图片
    private int code;
    // 消息时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
}
