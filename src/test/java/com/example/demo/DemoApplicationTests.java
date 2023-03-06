package com.example.demo;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DemoApplicationTests {

	@Autowired
	StringEncryptor stringEncryptor;

	@Test
	void contextLoads() {
		String e = JasyptUtils.encrypt("root");
		System.out.println(e);
		System.out.println(JasyptUtils.decrypt(e) + "???");
	}

	@Test
	public void test() {
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		// 盐值
		standardPBEStringEncryptor.setPassword("Saltvalue");
		// 加密明文
		String code = standardPBEStringEncryptor.encrypt("加密密文");
		// 第二次test,解密第一次test的密文
		System.out.println(standardPBEStringEncryptor.decrypt("uiqiG5IN/zZLExCSfnemxGZrIG9kStej"));
		System.out.println("code=" + code);
	}

}
