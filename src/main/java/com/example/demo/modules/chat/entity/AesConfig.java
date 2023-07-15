package com.example.demo.modules.chat.entity;


/**
 * @author PengPan
 * @version 1.0
 * @date 2020/7/16 15:41
 */
public class AesConfig {
    // 发送者
    private String data;
    // 接受者
    private String aesKey;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }
}
