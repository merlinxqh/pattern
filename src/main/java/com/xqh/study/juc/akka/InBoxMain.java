package com.xqh.study.juc.akka;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by leo on 2017/10/18.
 *  消息收件箱
 */
public class InBoxMain {
    public static void main(String[] args) throws TimeoutException {
        ActorSystem system=ActorSystem.create("inboxdemo", ConfigFactory.load("samplehello.conf"));
        ActorRef worker = system.actorOf(Props.create(MyWorker.class),"worker");

        final Inbox inbox = Inbox.create(system);
        inbox.watch(worker);
        inbox.send(worker,MyWorker.Msg.WORKING);
        inbox.send(worker,MyWorker.Msg.DONE);
        inbox.send(worker,MyWorker.Msg.CLOSE);
        while (true){
            Object msg=inbox.receive(Duration.create(1, TimeUnit.SECONDS));
            if(msg == MyWorker.Msg.CLOSE){
                System.out.println("My Worker is closing");
            }else if(msg instanceof Terminated){
                System.out.println("My worker is dead");
                system.shutdown();
                break;
            }else {
                System.out.println(msg);
            }
        }
    }
}
