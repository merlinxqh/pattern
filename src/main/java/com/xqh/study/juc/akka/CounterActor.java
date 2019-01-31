package com.xqh.study.juc.akka;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

/**
 * Created by leo on 2017/10/19.
 * 模拟10个actor一起对一个Agent执行累加操作, 每个actor累加10000次
 */
public class CounterActor extends UntypedActor {

    Mapper addMapper=new Mapper<Integer,Integer>() {
        @Override
        public Integer apply(Integer i) {
            return i+1;
        }
    };
    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Integer){
            for(int i=0;i<10000;i++){
                //我希望能知道future何时结束
                Future<Integer> f=AgentDemo.counterAgent.alter(addMapper);//alter指定累加动作
                AgentDemo.futures.add(f);
            }
            getContext().stop(getSelf());
        }else {
            unhandled(msg);
        }
    }
}
