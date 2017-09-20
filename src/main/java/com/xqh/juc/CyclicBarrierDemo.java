package com.xqh.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by leo on 2017/9/20.
 * 循环栅栏 (另一种多线程并发控制工具)
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{

        private String soldier;

        private final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic,String soldierName){
            this.cyclic=cyclic;
            this.soldier=soldierName;
        }


        @Override
        public void run() {
           //等待所有士兵到期
            try {
                /**
                 * 所有线程都在这里等待,知道所有士兵都集合完毕
                 * 意味着CyclicBarrier完成一次计数
                 */
                System.out.println(soldier+" 报道~~~~");
                cyclic.await();
                doWork();
                //等待所有士兵任务完成
                /**
                 * 再次调用await()方法时,会进行下一次计数
                 */
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() throws InterruptedException {
            Thread.sleep(3000);
            System.out.println(soldier+" 任务完成!");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

        public BarrierRun(boolean flag,int N){
            this.flag=flag;
            this.N=N;
        }

        @Override
        public void run() {
            if(flag){
                System.out.println("司令[士兵"+N+"个,任务完成!]");
            }else{
                System.out.println("司令[士兵"+N+"个,集合完成!]");
            }
        }
    }

    public static void main(String[] args) {
        final int N=10;
        Thread[] allSoldier=new Thread[N];
        boolean flag=false;
        CyclicBarrier cyclic=new CyclicBarrier(N,new BarrierRun(flag,N));
        //设置屏障点,主要是为了执行这个方法
        System.out.println("队伍集合!");
        for(int i = 0;i<N;++i){
            allSoldier[i]=new Thread(new Soldier(cyclic,"士兵"+i));
            allSoldier[i].start();
        }

    }
}
