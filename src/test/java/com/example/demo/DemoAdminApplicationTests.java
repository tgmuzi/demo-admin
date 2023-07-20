package com.example.demo;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.alibaba.druid.filter.config.ConfigTools.encrypt;
import static com.alibaba.druid.filter.config.ConfigTools.genKeyPair;

class DemoAdminApplicationTests {

	@Autowired
	StringEncryptor stringEncryptor;

	//DI注入数据源
	@Autowired
	DataSource dataSource;

	@Test
	void contextLoads() throws SQLException {
		//看一下默认数据源
		System.out.println(dataSource.getClass());
		//获得连接
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		DruidDataSource druidDataSource = (DruidDataSource) dataSource;
		System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
		System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
		//关闭连接
		connection.close();

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
	@Test
	public void databaseJieMi() throws Exception {
		String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALX6migCbkx3GKoqzk8pZm4DOUKLcNQRQKIpM+8ozY6548A7F7Lx8qieHq26cnIIihw2dbYNtLGAiISOBYg+bqUCAwEAAQ==";
		String password = "MK+90J8aAWQIjeL7+AFIq0dDQvUgXoih67jwPftfgs918tywCF9pCB3s6oY0apEo8mn+hClDWRmSeZr4mS2IiA==";
		System.out.println("PASSWORD:" + ConfigTools.decrypt(key,password));
	}

	public static void main(String[] args) throws Exception {
		String password = "900217ai";
//		String[] arr = genKeyPair(512);
//		System.out.println("privateKey:" + arr[0]);
//		System.out.println("publicKey:" + arr[1]);
//		System.out.println("password:" + encrypt(arr[0], password));
//
//		String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALX6migCbkx3GKoqzk8pZm4DOUKLcNQRQKIpM+8ozY6548A7F7Lx8qieHq26cnIIihw2dbYNtLGAiISOBYg+bqUCAwEAAQ==";
//		String password = "MK+90J8aAWQIjeL7+AFIq0dDQvUgXoih67jwPftfgs918tywCF9pCB3s6oY0apEo8mn+hClDWRmSeZr4mS2IiA==";
//		System.out.println("PASSWORD:" + ConfigTools.decrypt(key,password));

		String e = JasyptUtils.encrypt(password);
		System.out.println(e);
		System.out.println(JasyptUtils.decrypt(e) + "???");
	}
}
