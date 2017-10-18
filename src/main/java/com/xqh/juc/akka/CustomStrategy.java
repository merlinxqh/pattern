package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created by leo on 2017/10/18.
 */
public class CustomStrategy {

    public static void customStrategy(ActorSystem actorSystem){
       ActorRef ref=actorSystem.actorOf(Props.create(Supervisor.class),"Supervisor");
       ref.tell(Props.create(RestartActor.class),ActorRef.noSender());

        /**
         * ActorSelection用来批量发送消息
         */
       ActorSelection sel=actorSystem.actorSelection("akka://lifecycle/user/Supervisor/restartActor");
       for(int i=0;i<100;i++){
           sel.tell(RestartActor.Msg.RESTART, ActorRef.noSender());
       }
    }

    public static void main(String[] args) {
        ActorSystem system=ActorSystem.create("lifecycle", ConfigFactory.load("lifecycle.conf"));
        customStrategy(system);
    }
}
