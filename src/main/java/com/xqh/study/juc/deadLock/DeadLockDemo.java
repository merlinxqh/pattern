package com.xqh.study.juc.deadLock;

/**
 * Created by leo on 2017/10/5.
 */
public class DeadLockDemo extends Thread {

    protected Object tool;

    static Object fork1=new Object();
    static Object fork2=new Object();

    public DeadLockDemo(Object obj){
        this.tool=obj;
        if(tool == fork1){
            this.setName("哲学家A");
        }
        if(tool == fork2){
            this.setName("哲学家B");
        }
    }


    @Override
    public void run() {
        if(tool == fork1){
            synchronized (fork1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2){
                    System.out.println("哲学家A开始吃饭了...");
                }
            }
        }

        if(tool == fork2){
            synchronized (fork2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork1){
                    System.out.println("哲学家B开始吃饭了...");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLockDemo 哲学家A=new DeadLockDemo(fork1);
        DeadLockDemo 哲学家B=new DeadLockDemo(fork2);
        哲学家A.start();
        哲学家B.start();
        Thread.sleep(1000);
        /**
         * jdk提供的一套专业工具 查询死锁现象
         *  # jps
         *   12948 Jps
             7144
             10156 Launcher
             7596 DeadLockDemo
           # jstack.exe 7596

         */
    }
}
