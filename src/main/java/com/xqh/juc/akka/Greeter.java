package com.xqh.juc.akka;

import akka.actor.UntypedActor;

/**
 * Created by leo on 2017/10/17.
 * UntypedActor 就是我们所说的Actor
 */
public class Greeter extends UntypedActor{

    public static enum Msg{
        GREET,DONE;
    }
    @Override
    public void onReceive(Object message) {
        if(message == Msg.GREET){
            System.out.println("Hello world!");
            getSender().tell(Msg.DONE, getSelf());
        }else
            unhandled(message);
    }
}
