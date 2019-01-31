package com.xqh.study.juc.design.future;

/**
 * Created by leo on 2017/10/10.
 */
public class FutureData implements Data {

    protected RealData realData = null;  //FutureData是RealData的包装

    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData){
        if(isReady){
            return;
        }
        this.realData=realData;
        isReady=true;
        notifyAll();
    }

    @Override
    public synchronized String getResult() {
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return realData.result;
    }
}