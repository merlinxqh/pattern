package com.xqh.juc.akka;

import akka.actor.UntypedActor;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * Created by leo on 2017/10/19.
 */
public class EmployeeActor extends UntypedActor{

    private Ref.View<Integer> count= STM.newRef(50);

    @Override
    public void onReceive(Object msg) throws Exception {
        if(msg instanceof Coordinated){
            final Coordinated c= (Coordinated) msg;
            final int downCount = (Integer)c.getMessage();
            try {
                c.atomic(new Runnable() {
                    @Override
                    public void run() {
                        STM.increment(count, downCount);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if("GetCount".equals(msg)){
            getSender().tell(count.get(),getSelf());
        }else {
            unhandled(msg);
        }
    }
}
