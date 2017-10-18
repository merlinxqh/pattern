package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by leo on 2017/10/18.
 */
public class HelloWorld extends UntypedActor {

    ActorRef greeter;

    @Override
    /**
     * Akka回调方法,在Actor启动前, 会被Akka框架调用.;
     */
    public void preStart() throws Exception {
        greeter = getContext().actorOf(Props.create(Greeter.class),"greeter");
        System.out.println("Greeter Actor Path:"+ greeter.path());
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    @Override
    public void onReceive(Object message){
       if(message == Greeter.Msg.DONE){
           greeter.tell(Greeter.Msg.GREET,getSelf());
           getContext().stop(getSelf());
       }else
           unhandled(message);
    }
}
