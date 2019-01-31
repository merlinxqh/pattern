package com.xqh.study.juc.atomic.longAdder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by leo on 2017/10/17.
 */
public class LongAdderDemo {

    private static final int MAX_THREADS=3;            //线程数
    private static final int TASK_COUNT=3;             //任务数
    private static final int TARGET_COUNT=10000000;    //目标总数

    private static AtomicLong acount=new AtomicLong(0L);   //无锁原子操作
    private static LongAdder lacount=new LongAdder();
    private long count=0;

    static CountDownLatch cdlsync=new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdlatomic=new CountDownLatch(TASK_COUNT);
    static CountDownLatch cdladder=new CountDownLatch(TASK_COUNT);


    /**
     * 有所的加法
     * @return
     */
    protected synchronized long inc(){
        return ++count;
    }

    /**
     * 有锁的操作
     * @return
     */
    protected synchronized long getCount(){
        return count;
    }

    public static class SyncThread implements Runnable{
        protected String name;
        protected long starttime;
        LongAdderDemo out;

        public SyncThread(LongAdderDemo out,long starttime){
            this.out=out;
            this.starttime=starttime;
        }

        @Override
        public void run() {
            long v=out.getCount();
            while (v < TARGET_COUNT){
                v=out.inc();
            }
            long endtime=System.currentTimeMillis();
            System.out.println("SyncThread spend:"+(endtime-starttime)+" ms, v="+v);
            cdlsync.countDown();
        }
    }

    public void testSync() throws InterruptedException {
        ExecutorService exe= Executors.newFixedThreadPool(MAX_THREADS);
        long starttime=System.currentTimeMillis();
        SyncThread sync=new SyncThread(this,starttime);
        for(int i=0;i<TASK_COUNT;i++){
            exe.submit(sync);              //提交线程开始计算
        }
        cdlsync.await();
        exe.shutdown();
    }


    public static class AtomicThread implements Runnable{
        protected String name;
        protected long starttime;

        public AtomicThread(long starttime){
            this.starttime=starttime;
        }


        @Override
        public void run() {
            long v=acount.get();
            while (v < TARGET_COUNT){
                v = acount.incrementAndGet();
            }
            long endtime=System.currentTimeMillis();
            System.out.println("AtomicThread spend:"+(endtime-starttime)+" ms,v="+v);
            cdlatomic.countDown();
        }
    }

    public void testAtomic() throws InterruptedException {
        ExecutorService exe=Executors.newFixedThreadPool(MAX_THREADS);
        long starttime=System.currentTimeMillis();
        AtomicThread atomic=new AtomicThread(starttime);
        for(int i=0;i<TASK_COUNT;i++){
            exe.submit(atomic);
        }
        cdlatomic.await();
        exe.shutdown();
    }

    public static class AdderThread implements Runnable{

        protected String name;
        protected long starttime;

        public AdderThread(long starttime){
            this.starttime=starttime;
        }

        @Override
        public void run() {
           long v=lacount.sum();
           while (v < TARGET_COUNT){
               lacount.increment();
               v=lacount.sum();
           }
           long endtime=System.currentTimeMillis();
            System.out.println("AdderThread spend:"+(endtime-starttime)+" ms,v="+v);
            cdladder.countDown();
        }
    }

    public void testAdder() throws InterruptedException {
        ExecutorService exe=Executors.newFixedThreadPool(MAX_THREADS);
        long starttime=System.currentTimeMillis();
        AdderThread adder=new AdderThread(starttime);
        for(int i=0;i<TASK_COUNT;i++){
            exe.submit(adder);
        }
        cdladder.await();
        exe.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdderDemo demo=new LongAdderDemo();
        /**
         * 我电脑跑的结果, 跟书上结果不一样.... 吼吼
         */
        demo.testSync();  //363ms
        demo.testAtomic(); //189ms
        demo.testAdder(); //217ms
    }
}
