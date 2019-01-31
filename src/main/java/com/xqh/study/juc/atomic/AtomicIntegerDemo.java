package com.xqh.study.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 2017/9/30.
 */
public class AtomicIntegerDemo {

    static AtomicInteger i=new AtomicInteger();

    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for(int k=0;k<10000;k++){
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts=new Thread[10];
        for(int k=0;k<10;k++){
            ts[k]=new Thread(new AddThread());
        }
        for(int k=0;k<10;k++){
            ts[k].start();
        }
        for(int k=0;k<10;k++){
            ts[k].join();
        }
        System.out.println(i);
    }

}
