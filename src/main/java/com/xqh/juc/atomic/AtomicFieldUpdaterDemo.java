package com.xqh.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by leo on 2017/9/30.
 * 让普通变量也享受 原子操作
 */
public class AtomicFieldUpdaterDemo {

    public static class Candidate{
        int id;
        /**
         * 1. 变量不能设置为private
         * 2. 变量必须是volatile类型
         * 3. 由于CAS操作会通过对象实例中的偏移量直接进行赋值, 因此它 不支持static字段(Unsafe.objectFieldOffset()不支持静态变量)
         */
        volatile int score;
    }

    public static final AtomicIntegerFieldUpdater<Candidate> scoreUpdater=
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");
    //检查Updater是否正常工作
    public static AtomicInteger allScore=new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final Candidate stu=new Candidate();
        Thread[] t=new Thread[10000];
        for(int i=0;i<10000;i++){
            t[i]=new Thread(){
                @Override
                public void run() {
                    if(Math.random()>0.4){
                        scoreUpdater.incrementAndGet(stu);
                        allScore.incrementAndGet();
                    }
                }
            };
            t[i].start();
        }

        for(int i=0;i<10000;i++){
            t[i].join();
        }

        System.out.println("score="+stu.score);
        System.out.println("allScore="+allScore.get());
    }

}
