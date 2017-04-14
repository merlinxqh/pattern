package com.xqh.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    
	public static void main(String[] args) {
		
		CountDownLatch latch=new CountDownLatch(11);
		BlockingQueue<Runnable> blist=new ArrayBlockingQueue<>(100);
		ExecutorService es=new ThreadPoolExecutor(5, 100, 1, TimeUnit.MINUTES, blist);
		Thread t1=new Thread(new ThreadOne(latch),"线程1");
		Thread t2=new Thread(new ThreadOne(latch),"线程2");
		Thread t3=new Thread(new ThreadOne(latch),"线程3");
		Thread t4=new Thread(new ThreadOne(latch),"线程4");
		Thread t5=new Thread(new ThreadOne(latch),"线程5");
		Thread t6=new Thread(new ThreadOne(latch),"线程6");
		Thread t7=new Thread(new ThreadOne(latch),"线程7");
		Thread t8=new Thread(new ThreadOne(latch),"线程8");
		Thread t9=new Thread(new ThreadOne(latch),"线程9");
		Thread t10=new Thread(new ThreadOne(latch),"线程10");
		Thread t11=new Thread(new ThreadOne(latch),"线程11");
		es.execute(t1);
		es.execute(t2);
		es.execute(t3);
		es.execute(t4);
		es.execute(t5);
		es.execute(t6);
		es.execute(t7);
		es.execute(t8);
		es.execute(t9);
		es.execute(t10);
		es.execute(t11);
		es.shutdown();
		try {
			latch.await(5l,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ddddddddddd");
	}
}

class ThreadOne implements Runnable{
    private CountDownLatch latch;
	public ThreadOne(CountDownLatch latch){
		this.latch=latch;
	}
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" excute...");
		latch.countDown();
	}
	
}

