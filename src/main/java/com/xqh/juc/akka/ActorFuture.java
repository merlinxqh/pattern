package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;
/**
 * Created by leo on 2017/10/19.
 * 询问模式, Actor中的Future
 *   传统异步调用中,我们进行的是函数调用
 *   但是Actor是发送一条消息
 */
public class ActorFuture {
    public static void main(String[] args) throws Exception {
        ActorSystem system=ActorSystem.create("askDemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker=system.actorOf(Props.create(MyWorker.class),"worker");
        ActorRef printer=system.actorOf(Props.create(Printer.class),"printer");
        system.actorOf(Props.create(WatchActor.class,worker),"watcher");

        //等待future返回
        Future<Object> f=ask(worker, 5, 1500);
        //等待获取结果  同步等待
        int re = (int)Await.result(f, Duration.create(6, TimeUnit.SECONDS));
        System.out.println("result:"+re);

        //直接导向其他actor,pipe不会阻塞
        f=ask(worker, 6, 1500);
        pipe(f, system.dispatcher()).to(printer);

        worker.tell(PoisonPill.getInstance(), ActorRef.noSender());

    }
}
