package com.xqh.juc.suspend;

/**
 * Created by leo on 2017/9/15.
 * 不用suspend() 和 resume() 示例
 */
public class BadSuspend {

    public static Object u=new Object();

    static ChangeObjectThread t1=new ChangeObjectThread("t1");

    static ChangeObjectThread t2=new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread{

        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("int "+Thread.currentThread().getName());
                /**
                 * suspend()挂起线程 但是不会释放 锁资源,直到当前线程执行 resume()方法
                 *
                 * 如果resume()发生在suspend()方法前 就会出现 线程永远挂起的情况
                 */
                Thread.currentThread().suspend();//
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * t1启动 获取 u 对象锁
         */
        t1.start();
        Thread.sleep(100);
        /**
         * t2启动 在等待 u 对象锁
         */
        t2.start();

        /**
         * t1 释放 u 对象锁
         */
        t1.resume();
        /**
         * t2线程 执行 取消挂起 方法, 这时 可能 t2 suspend()挂起方法还未执行
         */
        t2.resume();
        /**
         * 主线程 等待t1结束
         */
        t1.join();
        /**
         * 主线程 等待t2结束
         * 因为 t2.suspend() 执行在 t2.resume() 之后
         * 所以t2线程 被永久挂起
         */
        t2.join();
    }
}
