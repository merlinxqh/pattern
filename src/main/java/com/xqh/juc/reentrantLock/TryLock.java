package com.xqh.juc.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by leo on 2017/9/18.
 * tryLock() 尝试申请锁资源,如果锁被占用,线程不进行等待 ,而是立即返回false
 */
public class TryLock implements Runnable {

    static ReentrantLock lock1=new ReentrantLock();

    static ReentrantLock lock2=new ReentrantLock();

    int lock;

    public TryLock(int lock){
        this.lock=lock;
    }

    @Override
    public void run() {
        if(lock == 1){
            while (true){
              if(lock1.tryLock()){
                  try {
                      try {
                          Thread.sleep(500);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }

                      if(lock2.tryLock()){
                          try {
                              System.out.println(Thread.currentThread().getId()+" my job done...");
                              return;
                          }finally {
                              if(lock2.isHeldByCurrentThread()){
                                  lock2.unlock();
                              }
                          }
                      }
                  }finally {
                      if(lock1.isHeldByCurrentThread()){
                          lock1.unlock();
                      }
                  }
              }
            }
        }else {
            while (true){
                if(lock2.tryLock()){
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(lock1.tryLock()){
                            try {
                                System.out.println(Thread.currentThread().getId()+" my job done...");
                                return;
                            }finally {
                                if(lock1.isHeldByCurrentThread()){
                                    lock1.unlock();
                                }
                            }
                        }
                    }finally {
                        if(lock2.isHeldByCurrentThread()){
                            lock2.unlock();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
       Thread t1=new Thread(new TryLock(1));
       Thread t2=new Thread(new TryLock(2));
       t1.start();
       t2.start();
    }
}
