package com.xqh.juc.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/9/20.
 * 倒计数器 (控制线程等待)
 */
public class CountDownLatchDemo implements Runnable{

    static final CountDownLatch latch=new CountDownLatch(10);

    static final CountDownLatchDemo demo=new CountDownLatchDemo();


    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("check complete");
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex= Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            ex.submit(demo);
        }
        //等待检查
        latch.await();
        System.out.println("火箭发射!");
        ex.shutdown();
    }
}
