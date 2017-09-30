package com.xqh.juc.threadLocal;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by leo on 2017/9/29.
 * 线程私有变量测试
 */
public class ThreadLocalTest {
    public static final int GEN_COUNT=10000000;

    public static final int THREAD_COUNT=4;

    static ExecutorService exe= Executors.newFixedThreadPool(THREAD_COUNT);

    public static Random rnd=new Random(123);

    public static ThreadLocal<Random> tRnd=new ThreadLocal<Random>(){
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    public static class RndTask implements Callable<Long>{
        private int model=0;

        public RndTask(int model){
            this.model=model;
        }

        public Random getRandom(){
            if(model==0){
                return rnd;
            }else if(model == 1){
                return tRnd.get();
            }else{
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b=System.currentTimeMillis();
            for(long i=0;i<GEN_COUNT;i++){
                getRandom().nextInt();
            }
            long e=System.currentTimeMillis();

            System.out.println(Thread.currentThread().getName()+" spend "+ (e-b)+" ms");

            return e-b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futs=new Future[THREAD_COUNT];
        for(int i=0;i<THREAD_COUNT;i++){
            futs[i]=exe.submit(new RndTask(0));
        }
        long totalTime=0;
        for(int i=0;i<THREAD_COUNT;i++){
            totalTime+=futs[i].get();
        }
        System.out.println("多线程访问同一个Random实例:"+totalTime +" ms");

        //ThreadLocal的情况
        for(int i=0;i<THREAD_COUNT;i++){
          futs[i]=exe.submit(new RndTask(1));
        }
        totalTime=0;
        for(int i=0;i<THREAD_COUNT;i++){
            totalTime+=futs[i].get();
        }
        System.out.println("使用ThreadLocal包装Random实例:"+totalTime+" ms");
        exe.shutdown();

        /**
         * pool-1-thread-2 spend 2834 ms
         pool-1-thread-1 spend 2888 ms
         pool-1-thread-3 spend 2913 ms
         pool-1-thread-4 spend 2919 ms
         多线程访问同一个Random实例:11554 ms
         pool-1-thread-3 spend 123 ms
         pool-1-thread-2 spend 124 ms
         pool-1-thread-1 spend 125 ms
         pool-1-thread-4 spend 128 ms
         使用ThreadLocal包装Random实例:500 ms
         */
    }


}
