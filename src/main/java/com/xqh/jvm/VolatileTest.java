package com.xqh.jvm;

import java.util.concurrent.CountDownLatch;

/**
 * volatile变量自增运算测试
 */
public class VolatileTest {
    public static volatile int race = 0;

    public static void increase(){
        race++;
    }

    private static final int THREADS_COUNT=20;

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        CountDownLatch latch = new CountDownLatch(THREADS_COUNT);
        for(int i = 0;i < THREADS_COUNT;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i<10000;i++){
                        increase();
                    }
                    latch.countDown();
                }
            });
            threads[i].start();
        }
        //等待所有累加线程都结束
        latch.await();

        System.out.println(race);
    }
}
