package com.xqh.juc;

/**
 * Created by leo on 2017/9/14.
 */
public class WaitNotifyTest {

    public static WaitNotifyTest test=new WaitNotifyTest();

    public static class WaitThread extends Thread{

        public WaitThread(String name){
            super(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" in...");
            try {
                System.out.println(Thread.currentThread().getName()+" waiting...");
                /**
                 * 必须要在 synchronized 语句中 执行 wait()方法
                 * wait()和notify()都需要首先获得目标对象的监视器.
                 *
                 */
                synchronized (test){
                    //取得test的监视器
                    test.wait();//当前线程加入到该对象的 等待队列中 , 等待 当前对象的 notify()方法 调用 并随机从队列中选取 将其唤醒.
                    //释放test的监视器
                }
                System.out.println(Thread.currentThread().getName()+" notify...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" out...");
        }
    }

    public static class NotifyThread extends Thread{

        @Override
        public void run() {
            synchronized (test){
                test.notify();//通知
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            WaitThread w=new WaitThread("wait thread "+i);
            w.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=0;i<5;i++){
            NotifyThread n=new NotifyThread();
            n.start();
        }
    }
}
