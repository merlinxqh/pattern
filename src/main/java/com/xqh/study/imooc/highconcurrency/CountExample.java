package com.xqh.study.imooc.highconcurrency;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 多线程 并发计数
 */
@Slf4j
public class CountExample {
    private static int threadTotal = 10;

    private static int clientTotal = 5000;

    private static long count = 0;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        for(int i = 0; i<clientTotal; i++){
            new Thread(new TestThread(semaphore), "thread-"+i).start();
        }
        exec.shutdown();
        log.info("count==>"+count);
    }

    public static void add(){
        System.out.println(Thread.currentThread().getName()+"===========> start");
        count++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class TestThread implements Runnable{

        private Semaphore semaphore;

        public TestThread(Semaphore semaphore){
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("current thread name == > "+Thread.currentThread().getName());
                Thread.sleep(1000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
