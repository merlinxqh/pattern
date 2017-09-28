package com.xqh.juc.lock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by leo on 2017/9/20.
 * 读写分离锁
 */
public class ReadWriteLockDemo {
    private static Lock lock=new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();

    private static Lock readLock=readWriteLock.readLock();

    private static Lock writeLock=readWriteLock.writeLock();

    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            return value;
        }finally {
            lock.unlock();
        }
    }

    public void hadleWrite(Lock lock,int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            this.value=index;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo=new ReadWriteLockDemo();

        Runnable readAble=new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(readLock);
//                    demo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeAble=new Runnable() {
            @Override
            public void run() {
               try {
                   demo.hadleWrite(writeLock, new Random().nextInt());
//                   demo.hadleWrite(lock,new Random().nextInt());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } finally {

               }
            }
        };

        for(int i=0;i<18;i++){
            new Thread(readAble).start();
        }

        for(int i=18;i<20;i++){
            new Thread(writeAble).start();
        }
        /**
         * 通过使用 读写分离锁, 提高读写性能.
         * 直接用重入锁, 需要耗时 20秒+, 读写分离锁 小于20秒
         */
    }


}
