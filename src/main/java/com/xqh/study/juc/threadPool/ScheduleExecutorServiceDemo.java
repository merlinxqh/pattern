package com.xqh.study.juc.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 2017/9/21.
 * 计划任务 调度
 */
public class ScheduleExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService schedule= Executors.newScheduledThreadPool(10);

        /**
         * 以上一个任务的开始时间为起点,延迟2秒执行下一个任务
         * 所以打印出时间间隔 是2秒
         */
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    /**
                     * 如果 这里的等待时间 超过 延迟 时间2秒,那么打印出的间隔时间 就是这里的等待时间
                     */
//                    Thread.sleep(8000);
                    System.out.println(System.currentTimeMillis()/1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0,2, TimeUnit.SECONDS);

        /**
         * 以上一个任务的结束时间为起点,延迟2秒执行下一个任务
         * 所以打印出时间间隔 是3秒
         */
//        schedule.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    System.out.println(System.currentTimeMillis()/1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        },0,2,TimeUnit.SECONDS);
    }
}
