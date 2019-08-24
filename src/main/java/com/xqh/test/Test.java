package com.xqh.test;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpMethods;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class Test {

	private static final String str="a";

	public static void main(String[] args) throws UnsupportedEncodingException, HttpProcessException, InterruptedException {
//		List<Object> obj = new ArrayList<>();
//		for(Object o : obj){
//		}
//		String data = "15488297844ODYxNjkxNjExMjA4ODMxuf_sqw2TOIgcXA6skNB/Lp7nHoHj8PiStXnk9TYcbogheUjTI8Tv/Ljiiczb5UcwRjpnioyVOrLpm58sHhf5Id1NPXE+Bs4M/63BCcibJpjrsg=";
//		System.out.println(getSHA1Digest(data));
//		buildTestSign();
//		System.out.println(URLEncoder.encode("+","UTF-8"));
//        System.out.println(getCurrentUnixTimestamp());
		List<String> list = Arrays.asList("1", "a", "d", "h", "ff");
		for(int i = 0 ;i < 100; i++){
			getRandom(list);
//			System.out.println(getRandom(list));
		}
	}


	public static String getRandom(List<String> list){
		Random random = new Random();
		int idx = random.nextInt(list.size());
		System.out.println(idx);
		return list.get(idx);
//		HttpConfig config = HttpConfig.custom()
//				.url("http://192.168.3.240:8811/testOpen?uri=object://mpRPCas9eDIiTcZ960GjTw==&deviceId=2&passPermitted=true")
//				.method(HttpMethods.POST);
//		for(int i = 0 ; i<3; i++){
//			try {
//				HttpClientUtil.sendAndGetResp(config);
//				Thread.sleep(1500);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		String test = "abcd,dfasdf";
//		HttpConfig config = HttpConfig.custom()
//				.url("http://192.168.3.240:8811/testOpen?uri=object://mpRPCas9eDIiTcZ960GjTw==&deviceId=2&passPermitted=true")
//				.method(HttpMethods.POST);
//		for(int i = 0 ; i<3; i++){
//			HttpClientUtil.sendAndGetResp(config);
//			Thread.sleep(1500);
//		}
//		System.out.println(test.substring(test.indexOf(",")+1));
//		String str = "abc,ddd";
//		System.out.println(str.substring(0, str.indexOf(",")));
	}

	public static void buildTestSign(){
		List<String> params = Lists.newArrayList();
		params.add("4");
		params.add("ODYxNjkxNjExMjA4ODMx");
		params.add("1548833795");
		params.add("uf_sqw2TOIgcXA6skNB/Lp7nHoHj8PiStXnk9TYcbogheUjTI8Tv/Ljiiczb5UcwRjpnioyVOrLpm58sHhf5Id1NPXE+Bs4M/63BCcibJpjrsg=");
		System.out.println(buildSignature(params));
	}

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
			log.error(e.getMessage(), e);
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

	public static void main11(String[] args) {
		for(int i = 0;i<10;i++){
			System.out.println(getOne());
		}
	}

	public static Map<String,String> getOne(){
		Long userId = (long)((Math.random()*9+1)*1000000);
		String uuid= UUID.randomUUID().toString().replaceAll("-","");
		Map<String,String> map= new HashMap<>();
		map.put("token",uuid+"_"+userId);
		map.put("userId",userId.toString());
		return map;
	}
	public static void main1(String[] args) throws Exception {
//		Integer a = 127;
//		Integer b = 127;
//		Integer c = 128;
//		Integer d = 128;
//		System.out.println(a == b);// 输出true
//		System.out.println(c == d);// 输出false
//		printOut(76234);
//        new Thread(new TestThread()).start();
//		new Thread(new TestThread2()).start();
		String key="tv:live:%s";
		System.out.println(String.format(key,"abc_ddd"));
	}

	public static class TestThread implements Runnable{
		@Override
		public void run() {
			test();
		}
	}

	public static class TestThread2 implements Runnable{
		@Override
		public void run() {
			test2();
		}
	}


	public static void test() {
		synchronized (str){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("get lock resource "+str);
		}
	}

	public static void test2(){
		synchronized ("a"){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("get lock resource aaaa");
		}
	}


	public static void printOut(int n){
		if(n >= 10){
			printOut(n/10);
		}
		printDigit(n % 10);
	}

	private static void printDigit(int i) {
		System.out.println(i);
	}
}
