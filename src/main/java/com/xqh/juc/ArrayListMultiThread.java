package com.xqh.juc;

import java.util.ArrayList;

/**
 * Created by leo on 2017/9/16.
 * ArrayList非线程安全
 * Vector 线程安全
 */
public class ArrayListMultiThread {
    static ArrayList<Integer> al=new ArrayList<>();

    public static class AddThread implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<1000000;i++){
                al.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new AddThread());
        Thread t2=new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(al.size());
    }
}
