package com.xqh.study.juc.lock.stampedLock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by leo on 2017/10/17.
 * StampedLock的小缺陷
 */
public class StampedLockCPUDemo {

    static Thread[] holdCpuThreads = new Thread[3];

    static final StampedLock lock=new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        /**
         * 写线程 一直占用 不释放锁
         */
        new Thread(){
            @Override
            public void run() {
                long readLong = lock.writeLock();
                LockSupport.parkNanos(600000000000L);
                lock.unlockWrite(readLong);
            }
        }.start();

        Thread.sleep(100);

        /**
         * 三个读线程 是挂起状态WAITING (parking)
         */
        for(int i=0;i<3;i++){
            holdCpuThreads[i]=new Thread(new HoldCpuReadThread());
            holdCpuThreads[i].start();
        }
        Thread.sleep(10000);
        /**
         * 中断三个读线程后, 此时CPU占用率 飙升.
         * 这是因为中断   导致park()函数返回. 使线程再次进入 运行状态 RUNNABLE
         * 它会一直存在并耗尽CPU资源,知道自己抢占到锁资源.
         */
        for(int i=0;i<3;i++){
            holdCpuThreads[i].interrupt();
        }
    }

    private static class HoldCpuReadThread implements Runnable{
        @Override
        public void run() {
            long lockr=lock.readLock();
            System.out.println(Thread.currentThread().getName()+" 获取读锁");
            lock.unlockRead(lockr);
        }
    }
}
