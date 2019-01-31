package com.xqh.study.juc.thread;

/**
 * Created by leo on 2017/9/16.
 * 线程优先级示例
 * 在Java中,使用1到10标识线程优先级
 */
public class PriorityDemo {

    public static class HighPriority extends Thread{
        static int count=0;

        @Override
        public void run() {
            while(true){
                synchronized (PriorityDemo.class){
                    count++;
                    if(count >  10000000){
                        System.out.println("HighPriority is completed...");
                        break;
                    }
                }
            }
        }
    }

    public static class LowerPriority extends Thread{
        static int count=0;

        @Override
        public void run() {
            while(true){
                synchronized (PriorityDemo.class){
                    count++;
                    if(count >  10000000){
                        System.out.println("LowerPriority is completed...");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread high=new HighPriority();
        Thread low=new LowerPriority();

        high.setPriority(Thread.MAX_PRIORITY);//设置最高优先级
        low.setPriority(Thread.MIN_PRIORITY);//设置最低优先级
        high.start();
        low.start();
    }
}
