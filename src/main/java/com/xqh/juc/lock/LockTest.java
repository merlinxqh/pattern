package com.xqh.juc.lock;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/26.
 */
public class LockTest implements Runnable{

    private static final Lock lock = new ReentrantLock();

    private static final Condition condition=lock.newCondition();


    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId()+" in...");
            Thread.sleep(3000);
            System.out.println("执行线程sleep 3秒...");
            condition.await();
            System.out.println(Thread.currentThread().getId()+" get notify and go on...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        Thread t1=new Thread(new LockTest());
//        t1.start();
//
//        Thread t2=new Thread(new LockTest());
//        t2.start();
//        Thread.sleep(7000);
//        lock.lock();
//        System.out.println("开始通知线程");
//        condition.signal();
//        Thread.sleep(3000);
//        System.out.println("sleep 3 seconds....");
//        lock.unlock();
//
        Queue<String> queue=new ConcurrentLinkedQueue<>();
        queue.add("a");
//        queue.add("b");
//        queue.add("c");
//        queue.add("d");
//        queue.add("e");
        queue.poll();
        System.out.println(JSON.toJSONString(queue));
      List<String> list=new CopyOnWriteArrayList<>();
      list.add("a");
//          //有界队列
        BlockingQueue queue2=new ArrayBlockingQueue(200);
    }
}
