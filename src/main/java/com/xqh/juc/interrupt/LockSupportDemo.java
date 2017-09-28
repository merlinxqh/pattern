package com.xqh.juc.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by leo on 2017/9/21.
 * 线程阻塞工具, 可以在线程内任意位置让线程阻塞
 * 优于 suspend() 和 resume()方法, 是 park()和unpark()执行顺序 不会导致线程永久性挂起
 *   这是因为   LockSupport类使用类似 信号量 的机制, 它为每一个线程准备了一个许可,如果许可可用,那么park()函数会立即返回,
 *   并且消费这个许可(也就是将许可变为不可用),如果许可不可用就会阻塞.
 *   而unpark()则使得一个许可变为可用(但是和信号量不同的是,许可不能累加,你不可能拥有超过一个许可,它永远只有一个)
 */
public class LockSupportDemo {

    public static Object u=new Object();

    static ChangeObjectThread t1=new ChangeObjectThread("t1");
    static ChangeObjectThread t2=new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread{

        public ChangeObjectThread(String name){
            super(name);
        }
        @Override
        public void run() {
           synchronized (u){
               System.out.println("in "+getName());
               LockSupport.park();//当前线程阻塞
           }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
