package com.xqh.study.juc.akka.particle;

import java.util.List;

/**
 * x 与 value 之间关系
 */
public class Fitness {

    public static double fitness(List<Double> x){
        double sum=0;
        for(int i=1;i<=4;i++){
            sum+=Math.sqrt(x.get(i));
        }
        return sum;
    }
}
