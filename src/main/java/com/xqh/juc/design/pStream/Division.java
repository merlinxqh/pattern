package com.xqh.juc.design.pStream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo on 2017/10/10.
 * 除法
 */
public class Division implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<>();

    @Override
    public void run() {
       while (true){
           try {
               Msg msg=bq.take();
               msg.i=msg.i/2;
               System.out.println(msg.orgStr+" = " + msg.i);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

       }
    }
}
