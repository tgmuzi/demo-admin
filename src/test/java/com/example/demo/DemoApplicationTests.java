package com.example.demo;

import com.alibaba.druid.filter.config.ConfigTools;
import junit.framework.TestCase;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static com.alibaba.druid.filter.config.ConfigTools.encrypt;
import static com.alibaba.druid.filter.config.ConfigTools.genKeyPair;

class DemoApplicationTests extends TestCase {

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
	@Test
	public void testPassword() throws Exception {
		String password = "";
		String[] arr = genKeyPair(512);
		System.out.println("privateKey:" + arr[0]);
		System.out.println("publicKey:" + arr[1]);
		System.out.println("password:" + encrypt(arr[0], password));
	}

	public static void main(String[] args) throws Exception {
		String password = "y2wc05s7ou1ea5ljtk@3K";
		String[] arr = genKeyPair(512);
		System.out.println("privateKey:" + arr[0]);
		System.out.println("publicKey:" + arr[1]);
		System.out.println("password:" + encrypt(arr[0], password));
//


//		String e = JasyptUtils.encrypt("900217ai");
//		System.out.println(e);
//		System.out.println(JasyptUtils.decrypt(e) + "???");
	}
}
