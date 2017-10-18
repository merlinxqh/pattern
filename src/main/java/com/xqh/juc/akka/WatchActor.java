package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by leo on 2017/10/18.
 * MyWorker监听者
 */
public class WatchActor extends UntypedActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    public WatchActor(ActorRef ref){
        getContext().watch(ref);
    }

    @Override
    public void onReceive(Object msg) {
       if(msg instanceof Terminated){
           System.out.println(String.format("%s has terminated, shutting down system",((Terminated)msg).getActor().path()));
           getContext().system().stop(getSelf());
       }else {
           unhandled(msg);
       }
    }
}
