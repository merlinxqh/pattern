package com.xqh.juc.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/18.
 * 锁申请等待限时
 * tryLock(param1,param2)
 * param1: 等待时长
 * param2: 时间单位
 * tryLock()无参数方法,如果锁被占用, 直接放弃申请
 */
public class TimeLock implements Runnable{

    static ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        try {
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getName()+" get lock success!");
                Thread.sleep(6000);
            }else{
                System.out.println(Thread.currentThread().getName()+" get lock failed!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1=new Thread(new TimeLock());
        Thread t2=new Thread(new TimeLock());
        t1.start();
        t2.start();
    }
}
