package com.xqh.study.juc.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.transactor.Coordinated;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Await;
import static akka.pattern.Patterns.ask;

import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 2017/10/19.
 * 软件事物内存
 * software transactional memory
 * 这里的事物和数据库事物很相似, 具有 隔离性,原子性,一致性  不具备持久性 (内存数据不会被保留下来)
 *
 * 以下例子 模拟 转账
 */
public class STMDemo {
    public static ActorRef company=null;
    public static ActorRef employee=null;

    public static void main(String[] args) throws Exception {
        final ActorSystem system=ActorSystem.create("transactionDemo", ConfigFactory.load("samplehello.conf"));
        company = system.actorOf(Props.create(CompanyActor.class),"company");
        employee = system.actorOf(Props.create(EmployeeActor.class),"employee");

        Timeout timeout= new Timeout(1, TimeUnit.SECONDS);
        for(int i=0;i<20;i++){
            company.tell(new Coordinated(i,timeout),ActorRef.noSender());
            Thread.sleep(200);
            Integer companyCount = (Integer)Await.result(ask(company, "GetCount", timeout),timeout.duration());

            Integer employeeCount = (Integer)Await.result(ask(employee, "GetCount", timeout),timeout.duration());

            System.out.println("company count="+companyCount);
            System.out.println("employee count="+employeeCount);
            System.out.println("====================");
        }
    }
}
