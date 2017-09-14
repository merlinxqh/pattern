package com.xqh.juc;

/**
 * Created by leo on 2017/9/13.
 */
public class MultiThreadLong {

    public static long t=0;

    private static Integer r1=0;

    private static Integer r2=0;

    private static Integer A=0;

    private static Integer B=0;

    public static class ChangeT implements Runnable{

        private long to;

        public ChangeT(long to){
            this.to=to;
        }

        @Override
        public void run() {
            while (true){
                MultiThreadLong.t=to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable{

        @Override
        public void run() {
           while (true){
               long tmp=MultiThreadLong.t;

               if(tmp != 111L && tmp != -999L && tmp != 333L && tmp != -444L){
                   System.out.println(tmp);
                   Thread.yield();//暂停当前正在执行的线程, 并执行其他线程 (这里 "其他" 包含 它自己)
               }
           }
        }
    }

    public static class Thread1 implements Runnable{
        @Override
        public void run() {
            MultiThreadLong.r2=MultiThreadLong.A;
            MultiThreadLong.B=1;
        }
    }

    public static class Thread2 implements Runnable{
        @Override
        public void run() {
            MultiThreadLong.r1=MultiThreadLong.B;
            MultiThreadLong.A=2;
        }
    }

    public static void main(String[] args) {
        /**
         * byte(整型)：8位，
         * short(整型)：16位，
         * char(字符型)：16位，
         * int(整型)：32位，
         * float(浮点型单精度)：32位，
         * long(整型)：64位，
         * double(浮点型双精度)：64位。
         */
//        new Thread(new ChangeT(111L)).start();
//        new Thread(new ChangeT(-999L)).start();
//        new Thread(new ChangeT(333L)).start();
//        new Thread(new ChangeT(-444L)).start();
//        new Thread(new ReadT()).start();
//        System.out.println(Long.toBinaryString(111L));
//        System.out.println(Integer.toBinaryString(111));

        new Thread(new Thread1()).start();
        new Thread(new Thread2()).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("r1 = "+MultiThreadLong.r1);
        System.out.println("r2 = "+MultiThreadLong.r2);
    }
}
