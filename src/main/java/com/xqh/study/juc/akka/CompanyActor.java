package com.xqh.study.juc.akka;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * Created by leo on 2017/10/19.
 *  公司转账actor
 */
public class CompanyActor extends UntypedActor {

    private Ref.View<Integer> count= STM.newRef(100);

    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Coordinated){
            final Coordinated c= (Coordinated) msg;
            final int downCount= (Integer) c.getMessage();
            STMDemo.employee.tell(c.coordinate(downCount),getSelf());
            try {
                c.atomic(new Runnable() {
                    @Override
                    public void run() {
                        if(count.get()<downCount){
                            throw new RuntimeException("company money:"+count.get()+" less than "+downCount);
                        }
                        STM.increment(count, -downCount);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("GetCount".equals(msg)){
            getSender().tell(count.get(), getSelf());
        }else {
            unhandled(msg);
        }
    }
}
