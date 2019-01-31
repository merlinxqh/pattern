package com.xqh.study.sync;

/**
 * Created by leo on 2017/9/14.
 */
public class SyncObj {

    public static SyncObj obj=new SyncObj();

    public static class ThreadOne extends Thread{
        public ThreadOne(String name){
            super(name);
        }
        @Override
        public void run() {
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+" in....");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" out....");
            }
        }
    }

    public static class ThreadTwo extends Thread{
        public ThreadTwo(String name){
            super(name);
        }
        @Override
        public void run() {
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+" in....");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" out....");
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            ThreadOne t=new ThreadOne("线程one "+i);
            ThreadTwo t2=new ThreadTwo("线程two "+i);
            t.start();
            t2.start();
        }
    }

}
