package com.xqh.juc.akka;

import akka.actor.UntypedActor;

/**
 * Created by leo on 2017/10/19.
 */
public class Printer extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Integer){
            System.out.println("Printer:"+msg);
        }else if(msg == MyWorker.Msg.DONE){
            System.out.println("Stop working");
        }else if(msg == MyWorker.Msg.CLOSE){
            System.out.println("I will shutdown");
            getSender().tell(MyWorker.Msg.CLOSE,getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(msg);
        }
    }
}
