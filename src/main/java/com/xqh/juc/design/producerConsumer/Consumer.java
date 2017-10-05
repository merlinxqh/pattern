package com.xqh.juc.design.producerConsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by leo on 2017/10/5.
 * 消费者
 */
public class Consumer implements Runnable {

    private BlockingQueue<PCData> queue;

    private static final int SLEEPTIME=1000;

    public Consumer(BlockingQueue<PCData> queue){
       this.queue=queue;
    }
    @Override
    public void run() {
        System.out.println("start consumer id= "+Thread.currentThread().getId());
        Random r=new Random();

        try {
            while (true){
                PCData data=queue.take();
                if(null != data){
                    int re= data.getData() * data.getData();//计算平方
                    System.out.println(MessageFormat.format("{0}*{1}={2}",data.getData(),data.getData(),re));
                }
                Thread.sleep(r.nextInt(SLEEPTIME));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.interrupted();
        }
    }
}
