package com.xqh.study.sync;

public class SyncEntity {
   
	public static void test1(){
		System.out.println("excute test1 ...");
	}
	
	public static void test2(){
		System.out.println("excute test2 ...");
	}
	
	public static void main(String[] args) {
		synchronized (SyncEntity.class) {
			SyncEntity.test1();
		}
	}
	
}
