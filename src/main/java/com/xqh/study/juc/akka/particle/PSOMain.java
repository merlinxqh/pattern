package com.xqh.study.juc.akka.particle;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * 粒子算法 主函数
 */
public class PSOMain {

    public static final int BIRD_COUNT=100000;

    public static void main(String[] args) {
        ActorSystem system=ActorSystem.create("psoSystem", ConfigFactory.load("samplehello.confs"));
        system.actorOf(Props.create(MasterBird.class),"masterBird");
        for(int i=0;i<BIRD_COUNT;i++){
            system.actorOf(Props.create(Bird.class),"bird_"+i);
        }
    }
}
