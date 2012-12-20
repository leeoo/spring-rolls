package org.dily.commons;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.dily.log.Log;
import org.dily.log.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.util.BigInt;

/**
 * 功 能 描 述:<br>
 * 字符编码工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-9上午10:28:44
 * <br>项 目 信 息:dily:org.dily.commons.EncodeUtils.java
 */
public abstract class EncodeUtils {
	
	private final static Log logger = LoggerFactory.getLogger();
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";
	
	/**
	 * 通过UUID获得唯一值
	 * @return
	 */
	public static String randomUUID(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获得当前纳秒值
	 * @return
	 */
	public static long nanoTime(){
		return System.nanoTime();
	}

	/**
	 * 获得当前豪秒值
	 * @return
	 */
	public static long millisTime(){
		return System.currentTimeMillis();
	}

	/**
	 * BASE64解密
	 * @param key
	 * @return
	 */
	public static byte[] decryptBASE64(String key){
		try {
			return (new BASE64Decoder()).decodeBuffer(key);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * BASE64加密
	 * @param key
	 * @return
	 */
	public static String encryptBASE64(byte[] key){
		try {
			return (new BASE64Encoder()).encodeBuffer(key);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * MD5加密
	 * @param data
	 * @return
	 */
	private static byte[] encryptMD5(byte[] data){
		try {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(data);
			return md5.digest();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * MD5加密
	 * @param data
	 * @return
	 */
	public static String encryptMD5(String data){
		try {
			byte[] md5 = encryptMD5(data.getBytes());
			return new BigInt(md5).toString().replaceAll(" ", "");
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * SHA加密
	 * @param data
	 * @return
	 */
	private static byte[] encryptSHA(byte[] data){
		try {
			MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
			sha.update(data);
			return sha.digest();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * SHA加密
	 * @param data
	 * @return
	 */
	public static String encryptSHA(String data){
		try {
			byte[] sha = encryptSHA(data.getBytes());
			return new BigInt(sha).toString().replaceAll(" ", "");
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 初始化HMAC密钥
	 * @return
	 */
	public static String initMacKey(){
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
			SecretKey secretKey = keyGenerator.generateKey();
			return encryptBASE64(secretKey.getEncoded());
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * HMAC加密
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encryptHMAC(byte[] data, String key) {
		try {
			SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			return mac.doFinal(data);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 将汉字转换成UTF-8的Unicode编码的十六进制编码,用于url传送中文值参数。
	 * @param str
	 * @return
	 */
	public static String encode(String str){
		try {
			return java.net.URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return str;
	}
	
}