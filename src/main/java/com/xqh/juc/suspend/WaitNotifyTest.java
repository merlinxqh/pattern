package com.xqh.juc.suspend;

/**
 * Created by leo on 2017/9/14.
 * 挂起,继续执行线程 示例
 * wait() notify()
 */
public class WaitNotifyTest {

    public static WaitNotifyTest test=new WaitNotifyTest();

    public static class WaitThread extends Thread{

        public WaitThread(String name){
            super(name);
        }

        @Override
        public void run() {
            synchronized (test){
                System.out.println(Thread.currentThread().getName()+" start...");
                try {
                    System.out.println(Thread.currentThread().getName()+" wait for object");
                    /**
                     * 必须要在 synchronized 语句中 执行 wait()方法
                     * wait()和notify()都需要首先获得目标对象的监视器.
                     *
                     */
                    Thread.sleep(2000);
                    //取得test的监视器
                    test.wait();//当前线程加入到该对象的 等待队列中 , 等待 当前对象的 notify()方法 调用 并随机从队列中选取 将其唤醒.
                    Thread.sleep(2000);
                    //释放test的监视器
                    System.out.println(Thread.currentThread().getName()+" get notify...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" end...");
            }
        }
    }

    public static class NotifyThread extends Thread{

        @Override
        public void run() {
            synchronized (test){
                System.out.println("start to notify one thread...");
                test.notify();//通知
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notify one thread end...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WaitThread w = new WaitThread("wait thread");
        w.start();
        WaitThread w2 = new WaitThread("wait thread 22222");
        w2.start();

        Thread.sleep(5000);
        NotifyThread n = new NotifyThread();
        n.start();

    }
}
