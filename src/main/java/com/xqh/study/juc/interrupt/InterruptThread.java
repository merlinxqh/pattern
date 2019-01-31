package com.xqh.study.juc.interrupt;

/**
 * Created by leo on 2017/9/14.
 */
public class InterruptThread {

    public static void main(String[] args) throws InterruptedException {
//        Thread t1=new Thread(){
//            @Override
//            public void run() {
//                while (true){
//                    if(Thread.currentThread().isInterrupted()){
//                        System.out.println("Interrupted!");
//                        break;
//                    }
//                    Thread.yield();
//                }
//            }
//        };
//        t1.start();
//        Thread.sleep(2000);
//        t1.interrupt();

        Thread t1=new Thread(){
            @Override
            public void run() {
                while (true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Interrupted!");
                        break;
                    }
                    try {
                        /**
                         * 如果在这行,线程被中断 会抛异常, 并且清除中断标识位
                         * 为保证数据一致性和完整性
                         * 在catch中再次加 中断标识位
                         * 在下次循环中 可正常中断线程
                         */
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted when sleep!");
                        //设置中断状态
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
