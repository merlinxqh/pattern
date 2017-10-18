package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import com.typesafe.config.ConfigFactory;

/**
 * Created by leo on 2017/10/18.
 */
public class RouteMain {

    public static Agent<Boolean> flag = Agent.create(true,ExecutionContexts.global());
    public static void main(String[] args) throws InterruptedException {
        ActorSystem system = ActorSystem.create("route", ConfigFactory.load("samplehello.conf"));
        ActorRef w = system.actorOf(Props.create(RouterActor.class),"watcher");
        int i=1;
        while (flag.get()){
            w.tell(MyWorker.Msg.WORKING, ActorRef.noSender());
            if(i%10 == 0){
                w.tell(MyWorker.Msg.CLOSE,ActorRef.noSender());
            }
            i++;
            Thread.sleep(100);
        }
    }
}
