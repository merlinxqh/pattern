package com.xqh.study.juc.akka.particle;

/**
 * 全局最优解的消息
 */
public final class GBestMsg {
   final PsoValue value;


    public GBestMsg(PsoValue value) {
        this.value = value;
    }

    public PsoValue getValue(){
        return value;
    }


}
