package com.xqh.juc.akka;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by leo on 2017/10/18.
 * 一个带有生命周期回调函数的Actor
 */
public class MyWorker extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    public static enum Msg{
        WORKING,DONE,CLOSE;
    }

    @Override
    public void preStart(){
        /**
         * 初始化资源
         */
        System.out.println("MyWorker is starting");
    }

    @Override
    public void postStop(){
        /**
         * 释放资源
         */
        System.out.println("MyWorker is stopping");
    }

    @Override
    public void onReceive(Object msg){
        if(msg instanceof Integer){
            int i=(Integer)msg;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getSender().tell(i*i,getSelf());
        }else if(msg == Msg.WORKING){
            System.out.println("I am working");
        }else  if(msg == Msg.DONE){
            System.out.println("Stop working");
        }else if(msg == Msg.CLOSE){
            System.out.println("I will shutdown");
            getSender().tell(Msg.CLOSE, getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(msg);
        }
    }
}
