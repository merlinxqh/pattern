package com.xqh.study.juc.design.pStream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo on 2017/10/10.
 * 乘法
 */
public class Multiply implements Runnable {
    public static BlockingQueue<Msg> bq=new LinkedBlockingQueue<>();

    @Override
    public void run() {
      while (true){
          try {
              Msg msg=bq.take();
              msg.i=msg.i * msg.j;
              Division.bq.add(msg);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }
}
