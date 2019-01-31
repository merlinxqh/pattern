package com.xqh.study.juc.suspend;

/**
 * Created by leo on 2018/2/2.
 */
public class WaitNotifyDemo {

    private static WaitNotifyDemo demo = new WaitNotifyDemo();

    public static class Thread1 implements Runnable{
        @Override
        public void run() {
            synchronized (demo){
                System.out.println(Thread.currentThread().getName()+"获取对象 demo 的锁");
                try {
                    System.out.println(Thread.currentThread().getName()+"线程挂起");
                    demo.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"执行完毕");
            }
        }
    }
    public static class Thread2 implements Runnable{
        @Override
        public void run() {
            synchronized (demo){
                System.out.println();
                System.out.println("==========通知所有线程==========");
                System.out.println();
               demo.notifyAll();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Thread1()).start();
        new Thread(new Thread1()).start();
        new Thread(new Thread1()).start();
        System.out.println("==========wait 5 seconds==========");
        Thread.sleep(5000);
        new Thread(new Thread2()).start();
    }
}
