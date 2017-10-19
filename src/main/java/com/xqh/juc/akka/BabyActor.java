package com.xqh.juc.akka;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;
import com.typesafe.config.ConfigFactory;

/**
 * Created by leo on 2017/10/19.
 * Actor内置状态转换
 */
public class BabyActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    public static enum Msg{
        SLEEP,PLAY,CLOSE
    }

    Procedure<Object> angry=new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            System.out.println("angryApply:"+message);
            if(message == Msg.SLEEP){
                getSender().tell("I am already angry",getSelf());
                System.out.println("I am already angry");
            }else if(message == Msg.PLAY){
                System.out.println("I like playing");
                getContext().become(happy);
            }
        }
    };

    Procedure<Object> happy=new Procedure<Object>() {
        @Override
        public void apply(Object message) throws Exception {
            System.out.println("happyApply:"+message);
            if(message == Msg.PLAY){
                getSender().tell("I am already happy :-)",getSelf());
                System.out.println("I am already happy :-)");
            }else if(message == Msg.SLEEP){
                System.out.println("I don't want to sleep");
                getContext().become(angry);
            }
        }
    };

    @Override
    public void onReceive(Object message) throws Exception {
        /**
         * become()方法用于切换actor状态
         */
        System.out.println("onReceive:"+message);
        if(message == Msg.SLEEP){
            getContext().become(angry);
        }else if(message == Msg.PLAY){
            getContext().become(happy);
        }else{
            unhandled(message);
        }
    }

    public static void main(String[] args) {
        ActorSystem system=ActorSystem.create("become", ConfigFactory.load("smpplehello.conf"));
        ActorRef child=system.actorOf(Props.create(BabyActor.class),"baby");
        system.actorOf(Props.create(WatchActor.class,child),"watcher");
        child.tell(Msg.PLAY,ActorRef.noSender());
        child.tell(Msg.SLEEP,ActorRef.noSender());
        child.tell(Msg.PLAY,ActorRef.noSender());
        child.tell(Msg.PLAY,ActorRef.noSender());

        child.tell(PoisonPill.getInstance(),ActorRef.noSender());
    }
}
