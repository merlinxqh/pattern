package com.xqh.juc.akka.particle;

/**
 * 表示携带个体最优解得消息
 */
public class PBestMsg {

    final PsoValue value;

    public PBestMsg(PsoValue value) {
        this.value = value;
    }

    public PsoValue getValue(){
        return value;
    }

    public String toString(){
        return value.toString();
    }
}
