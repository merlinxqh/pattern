package com.xqh.study.juc.volatileInfo;

/**
 * Created by leo on 2017/9/15.
 * 测试 volatile 原子性
 * 验证不能保证原子性操作
 */
public class AtomicTest {
    static volatile int i=0;

    public static Object obj=new Object();


    public static class PlusTask implements Runnable{
        @Override
        public void run() {
            for(int k=0;k<10000;k++)
               i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
       Thread[] list=new Thread[10];
        for(int j=0;j<10;j++){
           list[j]=new Thread(new PlusTask());
           list[j].start();
       }

       for(int j=0;i<10;j++){
            list[j].join();
       }
        System.out.println(i);
    }
}
