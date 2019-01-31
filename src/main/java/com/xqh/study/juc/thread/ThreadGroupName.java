package com.xqh.study.juc.thread;

/**
 * Created by leo on 2017/9/16.
 * 线程组
 */
public class ThreadGroupName implements Runnable {
    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName();

        while(true){
            System.out.println("I am "+groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg=new ThreadGroup("PrintGroup");
        Thread t1=new Thread(tg,new ThreadGroupName(),"T1");
        Thread t2=new Thread(tg,new ThreadGroupName(),"T2");
        t1.start();
        t2.start();
        /**
         * tg.activeCount()获取这个线程组活跃线程总数
         */
        System.out.println(tg.activeCount());
        /**
         * tg.list()可以打印出这个线程组所有的线程信息
         */
        tg.list();
    }
}
