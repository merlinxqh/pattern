package com.xqh.juc.reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/19.
 *
 */
public class ReentrantLockCondition implements Runnable{
    static ReentrantLock lock=new ReentrantLock();

    static Condition condition=lock.newCondition();
    @Override
    public void run() {

        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition r1=new ReentrantLockCondition();
        Thread t1=new Thread(r1);

        t1.start();
        Thread.sleep(2000);
        /**
         * 执行signal()时依然要先获取lock资源,执行完之后再释放lock资源, 跟notify()方法类似
         */
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}
