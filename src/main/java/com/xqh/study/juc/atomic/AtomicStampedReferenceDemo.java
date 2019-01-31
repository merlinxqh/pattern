package com.xqh.study.juc.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by leo on 2017/9/30.
 * 带有时间戳的 对象引用
 */
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<Integer> money=new AtomicStampedReference<>(19,0);

    public static void main(String[] args) {
       //模拟多个线程同时更新后台数据库,为用户充值
        for(int i=0;i<3;i++){
            final int timestamp=money.getStamp();
            new Thread(){
                @Override
                public void run() {
                   while (true){
                       while (true){
                           Integer m=money.getReference();
                           if(money.compareAndSet(m,m+20,timestamp,timestamp+1)){
                               System.out.println("账户余额小于20元,充值成功,余额:"+money.getReference()+"元");
                               break;
                           }else {
                               System.out.println("余额大于20元,无需充值");
                               break;
                           }
                       }
                       try {
                           Thread.sleep(100);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                }
            }.start();
        }
        new Thread(){
            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    while (true){
                        int timestamp=money.getStamp();
                        Integer m=money.getReference();
                        if(m>10){
                            System.out.println("余额大约10元");
                            if(money.compareAndSet(m,m-10,timestamp,timestamp+1)){
                                System.out.println("成功消费10元,余额:"+money.getReference());
                                break;
                            }else{
                                System.out.println("没有足够的余额");
                                break;
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
