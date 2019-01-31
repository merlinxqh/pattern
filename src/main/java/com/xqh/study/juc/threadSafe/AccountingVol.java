package com.xqh.study.juc.threadSafe;

/**
 * Created by leo on 2017/9/16.
 */
public class AccountingVol implements Runnable {

    static AccountingVol instance=new AccountingVol();

    /**
     * volatile只能保证 一个线程修改数据后,其他线程能够看到
     */
    static volatile int i=0;

    public void increase(){
        i++;
    }

    @Override
    public void run() {
       for(int j=0;j<10000000;j++){
           synchronized (instance){//实例加锁 和 increase()方法加synchronized效果一样
               increase();
           }
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        /**
         * java通过 synchronized 实现 线程同步 , 列出以下几种用法
         * 1. 指定加锁对象: 对给定对象加锁,进入同步代码前要获得给定对象的锁.
         * 2. 直接作用与实例的方法: 相当于对当前实例加锁,进入同步代码前必须先获得当前对象的锁.
         * 3. 直接作用于静态方法: 相当于对当前类加锁,进入同步代码前必须先获得当前类的锁.
         *
         *   除了线程同步,确保线程安全外, synchronized还可以保证线程的可见性和有序性.
         * 从可见性的角度来讲,synchronized完全可以替代volatile的功能,只是使用上没有那么方便.
         * 从有序性的角度来讲,由于synchronized限制每次只有一个线程可以访问同步快,因此同步块内部的
         * 代码如何被乱序打乱.
         */
    }
}
