package com.xqh.juc.design.producerConsumer.disruptor;

import com.lmax.disruptor.EventFactory;

public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
