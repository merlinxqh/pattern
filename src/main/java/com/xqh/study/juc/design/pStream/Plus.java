package com.xqh.study.juc.design.pStream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo on 2017/10/10.
 * P1 线程 计算加法
 */
public class Plus implements Runnable{

    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<>();

    @Override
    public void run() {
       while (true){
           try {
               Msg msg=bq.take();
               msg.j=msg.i+msg.j;
               Multiply.bq.add(msg);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
