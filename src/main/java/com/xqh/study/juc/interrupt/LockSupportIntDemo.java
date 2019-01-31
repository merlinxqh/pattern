package com.xqh.study.juc.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by leo on 2017/9/21.
 * lockSupport中断
 * 不会抛出异常InterruptedException,只会默默的返回,但是我们可以从Thread.interrupted()等方法中获取中断标志
 */
public class LockSupportIntDemo {

    static Object u=new Object();

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
               LockSupport.park();
               if(Thread.interrupted()){
                   System.out.println(getName()+" 被中断了...");
               }
           }
            System.out.println(getName()+" 执行结束...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();
        t1.interrupt();//中断t1
        LockSupport.unpark(t2);
    }

}
