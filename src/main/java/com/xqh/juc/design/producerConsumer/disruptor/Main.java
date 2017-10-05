package com.xqh.juc.design.producerConsumer.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exe= Executors.newCachedThreadPool();
        PCDataFactory factory=new PCDataFactory();
        /**
         * 设置缓冲区大小 2的整数次幂
         */
        int buffersize=1024;

        Disruptor<PCData> disruptor=new Disruptor<PCData>(factory,buffersize,exe, ProducerType.MULTI,new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(new Consumer(),new Consumer(),new Consumer(),new Consumer());
        disruptor.start();

        RingBuffer<PCData> ringBuffer=disruptor.getRingBuffer();

        Producer producer=new Producer(ringBuffer);

        ByteBuffer bb=ByteBuffer.allocate(8);

        for(long l=0; true; l++){
            bb.putLong(0,l);
            System.out.println("add data "+l);
            producer.pushData(bb);
            Thread.sleep(2000);
        }
    }
}
