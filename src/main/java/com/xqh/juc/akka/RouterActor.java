package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/10/18.
 * 路由管理
 */
public class RouterActor extends UntypedActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    public Router router;

    {
        List<Routee> routees=new ArrayList<>();
        for(int i=0;i<5;i++){
            ActorRef woker = getContext().actorOf(Props.create(MyWorker.class),"worker_"+i);
            getContext().watch(woker);
            routees.add(new ActorRefRoutee(woker));
        }
        /**
         * 路由策略
         * 1. RoundRobinRoutingLogic (轮询消息发送)
         * 2. BroadcastRoutingLogic  (广播策略)
         * 3. RandomRoutingLogic     (随机投递策略)
         * 4. SmallestMailboxRoutingLogic  (空闲Actor悠闲投递策略)
         */
        router=new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive(Object msg){
       if(msg instanceof MyWorker.Msg){
           /**
            * WORKING消息被轮流发送给 五个worker
            */
           router.route(msg,getSender());
       }else if(msg instanceof Terminated){
           router=router.removeRoutee(((Terminated)msg).actor());
           System.out.println(((Terminated)msg).actor().path()+" is closed,routees="+router.routees().size());
           if(router.routees().size() == 0){
               System.out.println("Close system");
               RouteMain.flag.send(false);
               getContext().system().shutdown();
           }
       }else {
           unhandled(msg);
       }
    }
}
