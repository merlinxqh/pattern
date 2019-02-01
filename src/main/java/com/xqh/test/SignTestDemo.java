package com.xqh.test;

import com.xqh.utils.Crypto3DES;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName SignTestDemo
 * @Description 获取 测试签名
 * @Author xuqianghui
 * @Date 2019/1/30 15:12
 * @Version 1.0
 */
public class SignTestDemo {

    private final static String timeStamp = "1548840427";

    public static void main(String[] args) {
//        buildAccessTokenSign();//获取accessToken 签名
//        buildEmailRegisterSign();//邮件注册用户 签名
//        buildSendActivityEmailSign();//发送激活邮件签名
//        buildThirdPartyRegisterSign();//第三方注册 签名
//        buildThirdPartyBindAccountSign();//第三方系统绑定现有账号
//        buildQRcodeLoginSign();//扫码登录
//        buildRefreshTokenSign();//刷新accessToken
        buildImgCodeSign();//生成图片验证码 签名
//        encodeStr("ua_Ngs4gzE3XX1ASwRh4hEZ0jHdNNRMyRWh3RL0bdKLtq8+Jv3BDWqvhi7kVfeD1hYAb3fkOBlTWACjMkUPNi0c2VbEBihuwkh+");
    }

    public static void encodeStr(String str){
        try {
            System.out.println(URLEncoder.encode(str, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static void testEncry(){
        String encryptedToken = "sqw2TOIgcXA6skNB/Lp7nHoHj8PiStXnk9TYcbogheUjTI8Tv/Ljiiczb5UcwRjpnioyVOrLpm58sHhf5Id1NPXE Bs4M/63BCcibJpjrsg=";
        String secretKey = "ceae0f85e47c41ef8505005e5b8a17f9";
        try {
            String resStr = Crypto3DES.Decrypt3DES(encryptedToken, secretKey);
            System.out.println("================>"+resStr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 扫码登录 签名
     */
    public static void buildQRcodeLoginSign(){
        List<String> params = commonParams();
        params.add(timeStamp);//timeStamp
        //accessToken
        params.add("ua_Ngs4gzE3XX1ASwRh4hEZ0jHdNNRMyRWh3RL0bdKLtq8+Jv3BDWqvhi7kVfeD1hYAb3fkOBlTWACjMkUPNi0c2VbEBihuwkh+");
        System.out.println(buildSignature(params));
    }

    /**
     * 获取 accessToken 参数 签名
     */
    public static void buildAccessTokenSign(){
        List<String> params = commonParams();
        params.add(timeStamp);//timeStamp
        //flushToken
        params.add("uf_f5l+NSUwoXzs9uEcPhDNgUk1YYCqOlMoRTOFrexbPPRWuzCdrmCgf3F7nVUmEGUv8FuYB1mA0pf0wtyZjPCQnUyDA3KTnVxf");
        System.out.println(buildSignature(params));
    }

    /**
     * 图片验证码  签名
     */
    public static void buildImgCodeSign(){
        List<String> params = commonParams();
        params.add(timeStamp);//timeStamp
        params.add("W3E14G");//imageId 页面生成的唯一标识
        System.out.println(buildSignature(params));
    }

    /**
     * 获取刷新accessToken 签名
     */
    public static void buildRefreshTokenSign(){
        List<String> params = commonParams();
        params.add("ua_Ngs4gzE3XX1ASwRh4hEZ0jHdNNRMyRWh3RL0bdKLtq8+Jv3BDWqvhi7kVfeD1hYAb3fkOBlTWACjMkUPNi0c2VbEBihuwkh+");
        params.add(timeStamp);
        System.out.println(buildSignature(params));
    }

    /**
     * 第三方注册 签名
     */
    public static void buildThirdPartyRegisterSign(){
        List<String> params = commonParams();
        params.add("2");//thirdPartyId
        params.add("657833103");//thirdPartyUserId
        params.add(timeStamp);
        System.out.println(buildSignature(params));
    }

    /**
     * 第三方系统 绑定现有账号
     */
    public static void buildThirdPartyBindAccountSign(){
        List<String> params = commonParams();
        params.add("2");//thirdPartyId
        params.add("502738919");//thirdPartyUserId
        params.add("qhx_kael@163.com");//account
        params.add("123456789");//密码 md5加密
        params.add(timeStamp);
        System.out.println(buildSignature(params));
    }

    /**
     * 邮箱注册 参数 签名
     */
    public static void buildEmailRegisterSign(){
        List<String> params = commonParams();
        params.add(timeStamp);//timeStamp
        params.add("xuqianghui@unisound.com");
        params.add("123456789");

        System.out.println(buildSignature(params));
    }

    /**
     * 发送激活邮件 签名
     */
    public static void buildSendActivityEmailSign(){
        List<String> params = commonParams();
        params.add("xuqianghui@unisound.com");
        params.add("http://www.baidu.com/");//redirectUrl

        System.out.println(buildSignature(params));
    }

    /**
     * 通用参数
     * @return
     */
    public static List<String> commonParams(){
        List<String> params = new ArrayList<>();
        params.add("2");//subSystemId
        params.add("120.237.156.138");//clientId
        return params;
    }
    /**
     * 对参数列表构造响应签名
     *
     * @param params
     * @return
     */
    public static String buildSignature(List<String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        // 升序排序参数值
        Collections.sort(params);

        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param == null ? "" : param);
        }

        return getSHA1Digest(sb.toString());
    }

    /**
     * 将字符串进行SHA1获取摘要，摘要为十六进制字符串
     *
     * @param data
     * @return
     */
    public static String getSHA1Digest(String data) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(data.getBytes("UTF-8"));
            digest = byte2hex(bytes);
        } catch (Exception e) {

        }

        return digest;
    }

    /**
     * 二进制转十六进制字符串
     *
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }

    /**
     * 获取当前UNIX的时间戳
     */
    public static long getCurrentUnixTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

}
