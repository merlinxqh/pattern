package com.xqh.juc.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/16.
 * ReentrantLock中断机制
 */
public class IntLock implements Runnable{

    public static ReentrantLock lock1=new ReentrantLock();

    public static ReentrantLock lock2=new ReentrantLock();

    int lock;

    public IntLock(int lock){
        this.lock=lock;
    }

    @Override
    public void run() {
        try {
           if(lock == 1){
               lock1.lockInterruptibly();
               Thread.sleep(500);
               lock2.lockInterruptibly();
           }else{
               lock2.lockInterruptibly();
               Thread.sleep(500);
               lock1.lockInterruptibly();
           }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId()+": 线程退出...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new IntLock(1));
        Thread t2=new Thread(new IntLock(2));
        t1.start();
        t2.start();
        Thread.sleep(1000);
        //中断其中一个线程
        t2.interrupt();
    }
}
