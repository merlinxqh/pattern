package com.xqh.study.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by leo on 2017/9/20.
 * 信号量 (允许多个线程同时访问)
 */
public class SemapDemo implements Runnable{
    final Semaphore semap=new Semaphore(5);
    @Override
    public void run() {
        try {
            semap.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+" done!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semap.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService ex= Executors.newFixedThreadPool(20);
        final SemapDemo demo=new SemapDemo();
        for(int i=0;i<20;i++){
            ex.execute(demo);
        }
        ex.shutdown();
    }
}
