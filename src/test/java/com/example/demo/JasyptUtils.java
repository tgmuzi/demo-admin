package com.example.demo;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * jasypt加解密工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/24 21:44
 * @since jdk1.8
 */
public class JasyptUtils {
    private JasyptUtils() {
    }

    private static String DEFAULT_KEY = "FKf1Oa+MMZPM8V2SUA1v3qIlkIN+SmpS";

    public static String encrypt(String content, String key) {
        StandardPBEStringEncryptor se = new StandardPBEStringEncryptor();
        se.setPassword(key);
        return se.encrypt(content);
    }

    public static String encrypt(String content) {
        return encrypt(content, DEFAULT_KEY);
    }

    public static String decrypt(String content, String key) {
        StandardPBEStringEncryptor se = new StandardPBEStringEncryptor();
        se.setPassword(key);
        return se.decrypt(content);
    }

    public static String decrypt(String content) {
        return decrypt(content, DEFAULT_KEY);
    }
}
