package com.xqh.study.juc.threadPool;

import java.util.concurrent.*;

/**
 * Created by leo on 2017/9/23.
 * 自定义线程池 和拒绝策略
 */
public class RejectThreadPoolDemo {

    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+": Thread ID: "+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        MyTask task=new MyTask();
//        ExecutorService exe=new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingDeque<>(10),
//                Executors.defaultThreadFactory(),
//                new RejectedExecutionHandler() {//自定义局决策略
//                    @Override
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        System.out.println(r.toString()+" is discard!");
//                    }
//                });
//        for(int i=0;i<Integer.MAX_VALUE;i++){
//            exe.submit(task);
//            Thread.sleep(10);
//        }

        MyTask task=new MyTask();
        ExecutorService exe=new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new ThreadFactory() {//自定义线程创建factory
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t=new Thread(r);
                        t.setDaemon(true);//设置为守护线程
                        System.out.println("create "+t);
                        return t;
                    }
                });
        for(int i=0;i<5;i++){
            exe.submit(task);
        }
        Thread.sleep(2000);
    }
}
