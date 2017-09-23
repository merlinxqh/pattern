package com.xqh.juc.threadPool;

import java.util.concurrent.*;

/**
 * Created by leo on 2017/9/21.
 * 线程池 示例
 */
public class ThreadPoolDemo {

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

    public static void main(String[] args) {
        ExecutorService exe= Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++){
            exe.submit(new MyTask());
        }
        /**
         * 线程池队列说明
         * 1. 直接提交的队列: SynchronousQueue,没有容量
         * 2. 有界的任务队列: ArrayBlockingQueue
         *     若有新的任务需要执行,
         *     若线程池实际线程数量小于corePoolSize,则会创建新的线程执行任务,
         *     若大于corePoolSize,则会将新任务加入等待队列.
         *     若等待队列已满 无法加入,则在线程总数不大于maximumPoolSize的前提下. 创建新的线程执行任务. 若大于maximumPoolSize则执行拒绝策略.
         * 3. 无界任务队列: LinkedBlockingQueue
         *      与有界队列相比,除非系统资源耗尽,否则无界队列不存在任务入队失败的情况.
         *      当有新的任务到来,系统线程数小于corePoolSize,系统创建新的线程执行任务,
         *      当系统线程数达到corePoolSize后,就不会继续增加,若后续仍有新的任务加入,
         *      而又没有空闲的线程资源,则任务进入等待队列.若任务创建和处理的速度差异很大,
         *      无界队列会保持快速增长,直到耗尽系统内存.
         *
         * 4. 优先任务队列: PriorityBlockingQueue,
         *      可以实现控制任务执行先后顺序,他是一个特殊的无界队列, 无论是ArrayBlockingQueue还是LinkedBlockingQueue都是按照先进先出的算法执行
         *      任务的. 而PriorityBlockingQueue是可以根据任务自身的优先级先后执行. 在确保系统性能的同时,也有很好的质量保证.
         */
        BlockingQueue<Runnable> blist=new ArrayBlockingQueue<>(2000);
        blist=new SynchronousQueue<>(true);
        blist=new LinkedBlockingDeque<>(200);
        blist=new PriorityBlockingQueue<>(10);
        ExecutorService pool=new ThreadPoolExecutor(10,20,60,TimeUnit.SECONDS,blist);
    }

}
