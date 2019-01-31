package com.xqh.study.juc.akka.particle;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 一个 基本 粒子
 */
public class Bird extends UntypedActor {
    private final LoggingAdapter log= Logging.getLogger(getContext().system(),this);

    private PsoValue pBest=null;  //个体最优

    private PsoValue gBest=null;  //全局最优

    /**
     * velocity 表示 粒子在各个维度上的速度.
     * 在当前案例中,每一年的投资额就可以认为是一个维度, 因此系统有四个维度.
     */
    private List<Double> velocity = new ArrayList<>(5);

    /**
     * x 表示投资方案
     */
    private List<Double> x = new ArrayList<>(5);

    private Random r=new Random();

    @Override
    public void preStart() throws Exception {
        for(int i = 0;i < 5; i++){
            velocity.add(Double.NEGATIVE_INFINITY);
            x.add(Double.NEGATIVE_INFINITY);
        }
        /**
         * x1,x2,x3,x4 分别代表第1,2,3,4年投资金额
         *  x1 <= 400
         *  x1 * 1.1 + x2 <= 400*1.1   (假设第一年全部存银行, 以下一次类推)
         *  (x1 * 1.1 + x2)*1.1 + x3 <= 400*1.1*1.1
         *  ((x1 * 1.1 + x2)*1.1 + x3)*1.1 + x4 <= 400*1.1*1.1*1.1
         */
        x.set(1, (double) r.nextInt(401));

        //x2 <= 440-1.1*x1
        double max = 440 - 1.1*x.get(1);
        if(max<0){
            max = 0;
        }
        //r.nextDouble() 获取的是 0 到 1 的 小数
        x.set(2, r.nextDouble()*max);

        //x3 <= 484-1.21*x1 -1.1*x2
        max = 484-1.21*x.get(1) - 1.1*x.get(2);
        if(max<0)max=0;
        x.set(3, r.nextDouble()*max);

        // x4 <= 532.4-1.321*x1-1.21*x2-1.1*x3
        max = 532.4-1.331*x.get(1)-1.21*x.get(2)-1.1*x.get(3);
        if(max<0)max=0;
        x.set(4,r.nextDouble()*max);

        double newFit=Fitness.fitness(x);
        pBest = new PsoValue(newFit, x);
        PBestMsg pBestMsg=new PBestMsg(pBest);
        ActorSelection  selection=getContext().actorSelection("/user/masterBird");
        selection.tell(pBestMsg, getSelf());//发送给Master
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        /**
         * Master计算出当前全局最优后, 会将全局最优发送给每一个粒子,
         * 粒子根据全局最优更新自己的运行速度, 并更新自己的速度以及当前位置.
         *
         * 粒子接收到全局最优解,
         */
       if(msg instanceof GBestMsg){
           gBest = ((GBestMsg)msg).getValue();
           //更新速度
           for(int i=0;i<velocity.size();i++){
               updateVelocity(i);
           }

           //更新位置
           for(int i=0;i<x.size();i++){
               updateX(i);
           }

           validateX();//用于约束粒子群
           double newFit=Fitness.fitness(x);

           if(newFit > pBest.getValue()){
               pBest = new PsoValue(newFit, x);
               PBestMsg pBestMsg= new PBestMsg(pBest);
               getSender().tell(pBestMsg, getSelf());
           }
       }else {
           unhandled(msg);
       }
    }

    private void validateX() {
        if(x.get(1) > 400){
            x.set(1, (double)r.nextInt(401));
        }

        //x2
        double max = 440 - 1.1*x.get(1);
        if(x.get(2)>max || x.get(2) < 0){
            x.set(2, r.nextDouble()*max);
        }

        //x3 <= 484-1.21*x1 -1.1*x2
        max = 484-1.21*x.get(1) - 1.1*x.get(2);
        if(x.get(3)>max || x.get(3)<0){
            x.set(3, r.nextDouble()*max);
        }

        // x4 <= 532.4-1.321*x1-1.21*x2-1.1*x3
        max = 532.4-1.331*x.get(1)-1.21*x.get(2)-1.1*x.get(3);
        if(x.get(4)>max || x.get(4) < 0){
            x.set(4,r.nextDouble()*max);
        }
    }

    /**
     * 速度 和位置的更新是根据 标准的 粒子群实现
     * @param i
     * @return
     */
    public double updateVelocity(int i){
        double v =Math.random()*velocity.get(i)
                + 2*Math.random()*(pBest.getX().get(i) - x.get(i))
                + 2*Math.random()*(gBest.getX().get(i) - x.get(i));
        v=v>0?Math.min(v, 5):Math.max(v,-5);
        velocity.set(i,v);
        return v;
    }

    public double updateX(int i){
        double newX = x.get(i)+velocity.get(i);
        x.set(i,newX);
        return newX;
    }
}
