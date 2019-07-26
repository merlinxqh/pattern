package com.xqh.utils;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * thread pool
 */
public class ThreadPoolUtils {

    private static ExecutorService executor;

    private static final int COOL_SIZE=100;//核心线程数量

    private static final int MAX_SIZE=200;//最大线程数量

    private static final int BLOCK_QUEUE_SIZE=3000*10*10*10;//等待队列

    private static final long TIME_OUT=60L;

    private static BlockingQueue<Runnable> blockQueue;

    private static ReentrantLock lock=new ReentrantLock();

    private static ExecutorService getInstance(){
        if(executor == null){
           lock.lock();
           if(executor == null){
               if(blockQueue == null){
                   blockQueue=new ArrayBlockingQueue<>(BLOCK_QUEUE_SIZE);
               }
               executor=new ThreadPoolExecutor(COOL_SIZE,MAX_SIZE,TIME_OUT,TimeUnit.SECONDS,blockQueue);
           }
           lock.unlock();
        }
        return executor;
    }

    public static void execute(Runnable t){
        getInstance().execute(t);
    }

    /**
     * 执行线程需要返回结果的方法
     * @param future
     */
    public static void submit(FutureTask<?> future){
        getInstance().submit(future);
    }

}
