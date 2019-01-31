package com.xqh.study.juc.design.pStream;

/**
 * Created by leo on 2017/10/10.
 */
public class PStreamMain {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Division()).start();

        for(int i=1;i<1000;i++){
            for(int j=1;j<1000;j++){
                Msg  msg=new Msg();
                msg.i=i;
                msg.j=j;
                msg.orgStr="(("+i+"+"+j+")*"+i+")/2";
                Plus.bq.add(msg);
            }
        }
    }
}
