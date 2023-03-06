package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exception.BusinessException;

import java.io.*;

/** */

/**
 * <p>
 * BASE64编码解码工具包
 * </p>
 * <p>
 * 依赖javabase64-1.3.1.jar
 * </p>
 * 
 * @author jun
 * @date 2012-5-19
 * @version 1.0
 */
public class Base64Utils {
	protected static Logger logger = LoggerFactory.getLogger(Base64Utils.class);
	/** */
	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 1024;

	/** */
	/**
	 * <p>
	 * BASE64字符串解码为二进制数据
	 * </p>
	 * 
	 * @param base64
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(String base64) throws RuntimeException {
		return Base64.decode(base64.getBytes());
	}

	public static String decode(byte[] b) {
		return new String(Base64.decode(b));
	}

	/** */
	/**
	 * <p>
	 * 二进制数据编码为BASE64字符串
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws RuntimeException {
		return new String(Base64.encode(bytes));
	}

	/** */
	/**
	 * <p>
	 * 将文件编码为BASE64字符串
	 * </p>
	 * <p>
	 * 大文件慎用，可能会导致内存溢出
	 * </p>
	 * 
	 * @param filePath
	 *                 文件绝对路径
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String encodeFile(String filePath) throws IOException {
		byte[] bytes = fileToByte(filePath);
		return encode(bytes);
	}

	/** */
	/**
	 * <p>
	 * BASE64字符串转回文件
	 * </p>
	 * 
	 * @param filePath
	 *                 文件绝对路径
	 * @param base64
	 *                 编码字符串
	 * @throws Exception
	 */
	public static void decodeToFile(String filePath, String base64)
			throws Exception {
		byte[] bytes = decode(base64);
		byteArrayToFile(bytes, filePath);
	}

	/** */
	/**
	 * <p>
	 * 文件转换为二进制数组
	 * </p>
	 * 
	 * @param filePath
	 *                 文件路径
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) {
		byte[] data = new byte[0];
		File file = new File(filePath);
		FileInputStream in = null;
		ByteArrayOutputStream out = null;
		if (file.exists()) {
			try {
				in = new FileInputStream(file);
				out = new ByteArrayOutputStream(2048);
				byte[] cache = new byte[CACHE_SIZE];
				int nRead = 0;
				while ((nRead = in.read(cache)) != -1) {
					out.write(cache, 0, nRead);
					out.flush();
				}
				data = out.toByteArray();
			} catch (Exception e) {
				logger.error(BusinessException.getCause(e) + "{}", e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error(BusinessException.getCause(e) + "{}", e);
					}
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						logger.error(BusinessException.getCause(e) + "{}", e);
					}
				}
			}
		}
		return data;
	}

	/** */
	/**
	 * <p>
	 * 二进制数据写文件
	 * </p>
	 * 
	 * @param bytes
	 *                 二进制数据
	 * @param filePath
	 *                 文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new ByteArrayInputStream(bytes);
			File destFile = new File(filePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			destFile.createNewFile();
			out = new FileOutputStream(destFile);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
		} catch (Exception e) {
			logger.error(BusinessException.getCause(e) + "{}", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error(BusinessException.getCause(e) + "{}", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(BusinessException.getCause(e) + "{}", e);
				}
			}
		}
	}

}
