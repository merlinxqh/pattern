package com.xqh.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/16.
 */
public class BadLockOnInteger implements Runnable{

    static Integer i=0;

    static BadLockOnInteger instance=new BadLockOnInteger();

    @Override
    public void run() {
        for(int j=0;j<10000;j++){
            /**
             * Integer属于不可变对象
             * Integer值变更只会重新创建Integer对象
             * 所以 这个 synchronized 起不到同步作用
             */
//            synchronized (i){
            synchronized (instance){//
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new BadLockOnInteger());
        Thread t2=new Thread(new BadLockOnInteger());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);

    }
}
