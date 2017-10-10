package com.xqh.juc.design.future;

/**
 * Created by leo on 2017/10/10.
 */
public class Client {

    public Data request(final String param){
        final FutureData future=new FutureData();
        new Thread(){
            @Override
            public void run() {
                RealData realData=new RealData(param);
                future.setRealData(realData);

            }
        }.start();
        return future;
    }
}
