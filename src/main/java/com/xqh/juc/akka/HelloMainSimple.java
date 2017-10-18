package com.xqh.juc.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created by leo on 2017/10/18.
 */
public class HelloMainSimple {

    public static void main(String[] args) {
        /**
         * 创建ActorSystem, 表示管理和维护Actor的系统.
         * 一般来说一个应用系统一个ActorSystem就够了.
         * 第一个参数 "Hello"为系统名称.
         * samplehello.conf 为配置文件
         */
        ActorSystem system=ActorSystem.create("Hello", ConfigFactory.load("samplehello.conf"));
        /**
         * 通过ActorSystem创建一个顶级的actor (HelloWorld)
         */
        ActorRef ref=system.actorOf(Props.create(HelloWorld.class),"helloWorld");
        System.out.println("HelloWorld actor path:"+ref.path());

        /**
         * 日志输出:
         *   HelloWorld actor path:akka://Hello/user/helloWorld
             Greeter Actor Path:akka://Hello/user/helloWorld/greeter
             Hello world!
             Hello world!
             [INFO] [10/18/2017 09:58:05.450] [Hello-akka.actor.default-dispatcher-2] [akka://Hello/user/helloWorld]
                 Message [com.xqh.juc.akka.Greeter$Msg] from Actor[akka://Hello/user/helloWorld/greeter#327136008]
                 to Actor[akka://Hello/user/helloWorld#934791718] was not delivered. [1] dead letters encountered.
                 This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and
                 'akka.log-dead-letters-during-shutdown'.

             第一行输出HelloWorld Actor的路径, 它是系统内第一个被创建的Actor.
             它的路径是 akka://Hello/user/helloWorld
             第一个Hello代表ActorSystem的系统名.
             第二个user 表示 用户Actor.所有的用户Actor都会挂载在use这个路径下.
             第三个helloWorld是这个Actor的名字

             [INFO] 内容, 表示系统遇到一条消息投递失败. 失败的原因是HelloWorld自己终止了, 导致Greeter发送的消息无法投递.
         */
    }
}
