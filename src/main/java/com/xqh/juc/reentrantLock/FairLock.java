package com.xqh.juc.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/19.
 * 公平锁
 * 要求系统维护一个 有序队列,因此公平锁实现成本比较高, 性能也相对低下, so默认情况下 是非公平锁.
 */
public class FairLock implements Runnable{

    static ReentrantLock lock=new ReentrantLock(true);

    @Override
    public void run() {

        while (true){
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+" 获取锁...");
            }finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {
        FairLock fair=new FairLock();
        Thread t1=new Thread(fair,"线程1");
        Thread t2=new Thread(fair,"线程2");
        t1.start();
        t2.start();
    }
}
