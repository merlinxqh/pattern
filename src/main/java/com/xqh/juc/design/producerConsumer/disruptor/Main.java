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

        Disruptor<PCData> disruptor=new Disruptor<PCData>(
                factory,
                buffersize,
                exe,
                ProducerType.MULTI,
                /**
                 * 消费者响应时间策略
                 * 1. BlockingWaitStrategy (默认策略)
                 *    最节省cpu,但是在高并发情况下,性能最糟糕
                 * 2. SleepingWaitStrategy
                 *    cpu使用率非常保守,适合对严实要求不高的场合, eg:异步日志
                 * 3. YieldingWaitStrategy
                 *    用于低延时的场合
                 * 4. BusySpinWaitStrategy
                 *    只有在对延时要求非常苛刻的情况下才考虑使用, 消费者线程会近最大努力疯狂监控缓冲区的变化. 会吃掉所有CPU资源.
                 */
                new BlockingWaitStrategy());
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
