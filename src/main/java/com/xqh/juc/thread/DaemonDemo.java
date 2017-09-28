package com.xqh.juc.thread;

/**
 * Created by leo on 2017/9/16.
 * 守护线程
 * (gc垃圾回收器,jit线程 即时编译器)
 */
public class DaemonDemo {

    public static class DaemonThread extends Thread{
        @Override
        public void run() {
            while(true){
                System.out.println("i am alive!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t=new DaemonThread();
        /**
         * 设置为守护线程
         * 并且必须在start()方法之前设置
         */
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);

        /**
         * 在一个java应用内, 只有守护线程 , jvm会自然退出....
         * t被当成 守护线程, 系统只有主线程main作为用户线程,因此在主线程main休眠2秒后 会自动退出
         */
    }
}
