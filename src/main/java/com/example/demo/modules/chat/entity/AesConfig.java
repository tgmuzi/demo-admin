package com.example.demo.modules.chat.entity;

import lombok.Data;

/**
 * @author PengPan
 * @version 1.0
 * @date 2020/7/16 15:41
 */
@Data
public class AesConfig {
    // 发送者
    private String data;
    // 接受者
    private String aesKey;
}
