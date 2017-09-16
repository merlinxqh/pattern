package com.xqh.juc.volatileInfo;

/**
 * Created by leo on 2017/9/15.
 * 可见性,有序性 测试
 */
public class NoVisibility {

    private static boolean ready;

    private static int number;

    public static class ReaderThread extends Thread{
        @Override
        public void run() {
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        Thread.sleep(1000);
        number=42;
        ready=true;
        Thread.sleep(10000);
        /**
         * 虚拟机client模式
         */
    }
}
