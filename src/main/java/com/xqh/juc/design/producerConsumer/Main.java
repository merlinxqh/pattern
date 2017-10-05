package com.xqh.juc.design.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo on 2017/10/5.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
       //建立缓冲区
        BlockingQueue<PCData> queue=new LinkedBlockingQueue<>(10);
        /**
         * 生产者
         */
        Producer p1=new Producer(queue);
        Producer p2=new Producer(queue);
        Producer p3=new Producer(queue);
        /**
         * 消费者
         */
        Consumer c1=new Consumer(queue);
        Consumer c2=new Consumer(queue);
        Consumer c3=new Consumer(queue);

        ExecutorService exe= Executors.newCachedThreadPool();
        exe.execute(p1);
        exe.execute(p2);
        exe.execute(p3);
        exe.execute(c1);
        exe.execute(c2);
        exe.execute(c3);
        Thread.sleep(10*1000);
        p1.stop();
        p2.stop();
        p3.stop();
        Thread.sleep(3000);
        exe.shutdown();
    }
}
