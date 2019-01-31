package com.xqh.study.juc.design.future;

/**
 * Created by leo on 2017/10/10.
 */
public class RealData implements Data {

    public final String result;

    public RealData(String para){
        //RealData  的构造可能很慢,需要用户等待很久, 这里使用sleep模拟
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<10;i++){
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result=sb.toString();

    }
    @Override
    public String getResult() {
        return result;
    }
}
