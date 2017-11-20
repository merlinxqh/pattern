package com.xqh;

import java.util.concurrent.ScheduledExecutorService;

public class Test {

	private static final String str="a";
	public static void main(String[] args) throws Exception {
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
