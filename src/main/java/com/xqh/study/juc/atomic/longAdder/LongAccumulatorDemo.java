package com.xqh.study.juc.atomic.longAdder;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * Created by leo on 2017/10/17.
 * LongAccumulator 是 LongAdder的亲兄弟, 它们公共的父类Striped64.
 * 因此两者内部优化方式是一样的,它们都将一个long整数进行分割,存储在不同的变量中.
 * 以防止多线程竞争. 但是 LongAccumulator是LongAdder的功能拓展,
 * 对于LongAdder来说,它只是每次给指定的整数执行一次加法,
 * 而LongAccumulator则可以实现任意函数操作.
 */
public class LongAccumulatorDemo {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 构造LongAccumulator, 因为要过滤最大值, 因此传入 Long::max 函数句柄.
         * 当有数据通过accumulate()方法传入LongAccumulator后,
         * LongAccumulator会通过 Long::max 识别最大值,并且保存在内部 (很可能是cell数组,也可能是base)
         * 最后通过 longValue()方法对所有的cell进行 Long::max 操作, 取得最大值.
         */
        LongAccumulator lator=new LongAccumulator(Long::max, Long.MIN_VALUE);
        Thread[] ts=new Thread[1000];
        for(int i=0;i<1000;i++){
            ts[i]=new Thread(() -> {
                Random random=new Random();
                long value=random.nextLong();
                lator.accumulate(value);
            });
            ts[i].start();
        }
        for(int i=0;i<1000;i++){
            ts[i].join();
        }
        System.out.println(lator.longValue());
    }

}
