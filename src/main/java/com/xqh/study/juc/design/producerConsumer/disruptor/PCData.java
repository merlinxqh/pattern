package com.xqh.study.juc.design.producerConsumer.disruptor;

public class PCData {
    private long value;

    public void set(long value){
        this.value=value;
    }

    public long get(){
        return value;
    }
}
