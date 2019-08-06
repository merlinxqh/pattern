package com.xqh.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author      leo
 * @description 常用加密工具类
 * @date        
 */
public class EncryptUtils {
	
	private static Logger logger= LoggerFactory.getLogger(EncryptUtils.class);
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

    private final static String CHARSET_NAME = "UTF-8";

    private final static String ENCRYPT_AES = "AES";

    public static String urlDecode(String str){
        try {
            return  URLDecoder.decode(str, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String urlEncode(String str){
        try {
            return URLEncoder.encode(str, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64 编码
     * @param encryptStr
     * @return
     */
    public static String base64Encode(String encryptStr){
        try {
            return Base64.encodeBase64URLSafeString(encryptStr.getBytes(CHARSET_NAME));
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return encryptStr;
    }

    /**
     * encode string
     * @param str
     * @return
     */
    public static String encodeString(String str){
        try {
            return URLEncoder.encode(str, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return str;
    }

    /**
     * base64 解码
     * @param str
     * @return
     */
    public static String base64Decode(String str){
        try {
            return new String(Base64.decodeBase64(str.getBytes(CHARSET_NAME)), CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        }
        return str;
    }
    
    public static void main(String[] args) {
        System.out.println(encryPwd("house@ym12#"));
	}
    
    /**
     * 用户密码加密
     * @param password
     * @return
     */
    public static String encryPwd(String password){
    	return getMd5(getSha1(password));
    }

    /**
     * 参数 按ascii码排序 拼接
     * @param params
     * @return
     */
    public static String buildSignStr(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            if (sb.length()!=0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
    
    
    public static String getSha1(String string) {
		return encryptCalc(string,"SHA1");
	}
    
    public static String getMd5(String str){
    	return encryptCalc(str, "MD5");
    }


    /**
     * 敏识 token 加密
     * @param key
     * @param token
     * @return
     */
    public static String getMinshiToken(String key, MinshiToken token){
        SecretKeySpec keySpec = new SecretKeySpec(java.util.Base64.getDecoder().decode(key), ENCRYPT_AES);
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return java.util.Base64.getEncoder().encodeToString(
                    cipher.doFinal(jsonMapper.writeValueAsBytes(token)));
        } catch (NoSuchAlgorithmException e) {
            logger.error("", e);
        } catch (NoSuchPaddingException e) {
            logger.error("", e);
        } catch (InvalidKeyException e) {
            logger.error("", e);
        } catch (BadPaddingException e) {
            logger.error("", e);
        } catch (JsonProcessingException e) {
            logger.error("", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("", e);
        }
        return null;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MinshiToken{

        private String userName;

        private Long validTimeStart;

        private Long validTimeEnd;
    }


	/**
     * SHA1 加密
     * @param str
     * @return
     */
    public static String encryptCalc(String str, String encryType){
        if (null == str || 0 == str.length()){
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(encryType);
            mdTemp.update(str.getBytes("UTF-8"));
             
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
        	logger.error("",e);
        } catch (UnsupportedEncodingException e) {
        	logger.error("",e);
        }
        return null;
    }
}
