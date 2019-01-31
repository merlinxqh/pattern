package com.xqh.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

@SuppressWarnings("restriction")
@Slf4j
public class Crypto3DES {
	// 定义加密算法,可用 DES,DESede,Blowfish
	private static final String Algorithm = "DESede";

	/**
	 * 使用3DES算法加密数据
	 * 
	 * @param data
	 *            要加密的数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt3DES(String data, String key) throws Exception {
		String str = byte2Base64(encryptMode(GetKeyBytes(key), data.getBytes()));
		return str;
	}

	/**
	 * 使用3DES算法解密数据
	 * 
	 * @param data
	 *            要解密的数据
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt3DES(String data, String key) throws Exception {
		byte[] b = decryptMode(GetKeyBytes(key), Base64.decode(data));
		return new String(b);
	}

	/**
	 * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
	 * 
	 * @param strKey
	 * @return
	 * @throws Exception
	 */
	private static byte[] GetKeyBytes(String strKey) throws Exception {
		if (null == strKey || strKey.length() < 1) {
			throw new Exception("key is null or empty!");
		}

		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(strKey.getBytes());
		byte[] bkey = alg.digest();
		int start = bkey.length;
		byte[] bkey24 = new byte[24];
		for (int i = 0; i < start; i++) {
			bkey24[i] = bkey[i];
		}

		// 为了与.net16位key兼容
		for (int i = start; i < 24; i++) {
			bkey24[i] = bkey[i - start];
		}

		return bkey24;
	}

	/**
	 * keyBytes为加密密钥，长度为24字节, src为被加密的数据缓冲区（源）
	 * 
	 * @param keyBytes
	 * @param src
	 * @return
	 */
	private static byte[] encryptMode(byte[] keyBytes, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);

			return c1.doFinal(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * keyBytes为加密密钥，长度为24字节, src为加密后的缓冲区
	 * 
	 * @param keyBytes
	 * @param src
	 * @return
	 */
	private static byte[] decryptMode(byte[] keyBytes, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);

			return c1.doFinal(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * 转换成base64编码
	 * @return
	 */
	private static String byte2Base64(byte[] bytes) {
		return Base64.encode(bytes);
	}

	public static void main(String[] args) {
		String key = "abcd1234";
		String password = "password";

		log.debug("key=" + key + ",password=" + password);
		log.debug("----------示例开始：使用java写的算法加密解密-----------");
		try {
			String encrypt = Crypto3DES.Encrypt3DES(password, key);
			String decrypt = Crypto3DES.Decrypt3DES(encrypt, key);

			log.debug("调用原始密钥算加密结果:" + encrypt);
			log.debug("调用原始密钥算解密结果:" + decrypt);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		log.debug("----------示例结束：使用java写的算法加密解密-----------");
	}
}