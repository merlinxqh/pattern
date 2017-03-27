package com.xqh.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    
	public static void main(String[] args) {
		ExecutorService es=Executors.newFixedThreadPool(2);
		Executors.newCachedThreadPool();
//		es=Executors.newSingleThreadExecutor();
		es=Executors.newCachedThreadPool();
		Thread t1=new Thread(new ThreadOne());
		Thread t2=new Thread(new ThreadOne());
		Thread t3=new Thread(new ThreadOne());
		Thread t4=new Thread(new ThreadOne());
		Thread t5=new Thread(new ThreadOne());
		es.execute(t1);
		es.execute(t2);
		es.execute(t3);
		es.execute(t4);
		es.execute(t5);
		es.shutdown();
	}
}

class ThreadOne implements Runnable{

	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" excute...");
	}
	
}

