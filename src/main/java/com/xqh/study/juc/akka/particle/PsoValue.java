package com.xqh.study.juc.akka.particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 表示一个可行的解
 */
public final class PsoValue {
    /**
     * 表示这种投资方案的收益值
     */
    final double value;
    /**
     * 数组 x[1],x[2],x[3],x[4] 分别表示第1,2,3,4年的投资金额.
     */
    final List<Double> x;


    public PsoValue(double v, List<Double> x) {
        this.value = v;
        List<Double> b=new ArrayList<>(5);
        b.addAll(x);
        this.x= Collections.unmodifiableList(b);
    }

    public double getValue() {
        return value;
    }

    public List<Double> getX() {
        return x;
    }

    @Override
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("value:").append(value).append("\n").append(x.toString());
        return sb.toString();
    }
}
