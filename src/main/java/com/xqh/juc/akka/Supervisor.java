package com.xqh.juc.akka;

import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 2017/10/18.
 *  监督策略
 *  1. OneForOneStrategy 父Actor只会对出问题的子Actor进行处理.
 *  2. AllForOneStrategy 父Actor会对出问题子Actor以及它所有的兄弟Actor都进行处理
 */
public class Supervisor extends UntypedActor {

    private static SupervisorStrategy strategy=new OneForOneStrategy(3, Duration.create(1, TimeUnit.SECONDS), new Function<Throwable, SupervisorStrategy.Directive>() {
        @Override
        public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
            if(t instanceof ArithmeticException){
                System.out.println("meet ArithmeticException ,just resume");
                return SupervisorStrategy.resume();
            }else if(t instanceof NullPointerException){
                System.out.println("meet NullPointerException");
                return SupervisorStrategy.restart();
            }else if(t instanceof IllegalArgumentException){
                return SupervisorStrategy.stop();
            }else {
                return SupervisorStrategy.escalate();
            }
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object o) {
        if(o instanceof Props){
            getContext().actorOf((Props)o,"restartActor");
        }else {
            unhandled(o);
        }
    }
}
