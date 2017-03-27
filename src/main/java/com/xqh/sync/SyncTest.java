package com.xqh.sync;

public class SyncTest {
	
	/**
	 * 
	 */
	public void test1(){
		//this对象实例
		synchronized (SyncTest.class) {
			int i=5;
			while(i-- >0){
				System.out.println(Thread.currentThread().getName()+":"+i);
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
			}
		}
	}
	
	public synchronized void test2(){
		int i=5;
		while(i-- >0){
			System.out.println(Thread.currentThread().getName()+":"+i);
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
			}
		}
	}
	
	public void test3(){
		System.out.println(Thread.currentThread().getName()+":执行方法test3");
	}
	
	/**
	 * synchronized XXXX.class 和  synchronized XXXX的 static方法 效果是一样的
	 * synchronized 同步当前对象 , 或者当前对象的一个方法 效果也是一样的 
	 * 对象锁  和 类锁 (类锁 是抽象出来的一个概念) 是两个不一样的锁,控制着不同的区域,他们互相互不干扰
	 * 
	 * synchronized 同步代码块 优于 同步方法/or实例, 因为  对象锁 会导致其他线程访问不了 该对象的其他 同步 方法
	 * 
	 * 同步代码块可以 同步需要同步的代码   
	 * @param args
	 */
	
	public static void main111(String[] args) {
		
		//两种方式都是对象锁
		final SyncTest sy=new SyncTest();
		
		Thread t1=new Thread(new Runnable() {
			public void run() {
				sy.test1();
			}
		},"test1");
		
		Thread t2=new Thread(new Runnable() {
			public void run() {
				sy.test2();
			}
		},"test2");
		
		Thread t3=new Thread(new Runnable() {
			public void run() {
				sy.test3();
			}
		},"test2");
		t1.start();
		t2.start();
	}
	
	
	public static void main(String[] args) {
		Thread t1=new Thread(new Runnable() {
			public void run() {
				synchronized (SyncEntity.class) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SyncEntity.test1();
				}
			}
		},"test1");
		
		Thread t2=new Thread(new Runnable() {
			public void run() {
				synchronized (SyncEntity.class) {
					SyncEntity.test2();
				}
			}
		},"test2");
		t1.start();
		t2.start();
	}

}
