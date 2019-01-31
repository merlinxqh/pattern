package com.xqh.study.jvm;

public class GCTest {
    private static final int _1M=1024*1024;


    public static void main(String[] args) {
//        minorGc();
//        bigObjJoinOld();
//        testTenuringThreshold();
//        testTenuringThreshold2();
        handlePormotionFailure();
    }

    /**
     * 新生代Minor GC
     * VM参数 -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void minorGc(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1= new byte[2 * _1M];
        allocation2= new byte[2 * _1M];
        allocation3= new byte[2 * _1M];
        allocation4= new byte[2 * _1M]; //出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     * VM参数: -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728 设置大于3M的对象直接进入老年代
     */
    public static void bigObjJoinOld(){
        byte[] allocation=new byte[4*_1M];
    }

    /**
     * 长期存活的对象将直接进入老年代
     * 对象年龄: 对象在Survivor区中 熬过 一次 Minor GC 年龄就增加1 (默认对象年龄达到15时,直接进入老年代)
     * VM参数:-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=1 手动 设置对象年龄阈值 测试 该值 为 1 和 15 两种情况
     * -XX:+PrintTenuringDistribution
     *
     */
    public static void testTenuringThreshold(){
        byte[] allocation1,allocation2,allocation3;
        allocation1=new byte[_1M/4];
        allocation2=new byte[4 * _1M];
        allocation3=new byte[4 * _1M];
        allocation3=null;
        allocation3=new byte[4 * _1M];
    }

    /**
     * 动态对象年龄判定
     *  为了能更好滴适应不同程序的内存状况,虚拟机并不是永远第要求对象的年龄必须达到了MaxTenuringThreshold才能晋升老年代,
     *  如果在Survivor空间中相同年龄所有对象的大小总和大于Survivor空间的一半, 年龄大于或等于该年龄的对象就可以直接进入老年代,
     *  无需等到MaxTenuringThreshold中要求的年龄.
     *  VM参数:-XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *  -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold2(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[_1M / 4];
        //allocation1 + allocation2 大于Survivor空间的一半
        allocation2 = new byte[_1M / 4];
        allocation3 = new byte[4*_1M];
        allocation4 = new byte[4*_1M];
        allocation4 = null;
        allocation4 = new byte[4*_1M];
    }

    /**
     * 空间分配担保
     *   在发生Minor GC之前,虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间,如果这个条件成立, nameMinor GC可以确保是安全的.
     *   如果不成立, 则虚拟机会查看HandlePromotionFailure设置值是否允许担保失败. 如果允许, 那么会继续检查老年代最大可用的连续空间是否大于
     *   历次晋升到老年代对象的平均大小,如果大于 就尝试进行一次Minor GC, 尽管这次Minor GC是有风险的; 如果小于,或者HandlePromotionFailure
     *   设置为不允许冒险, 那么这时也要改为 进行一次Full GC
     *
     *   为避免Full GC过于频繁, 大部分情况下HandlePromotionFailure开关会打开.
     *
     *   VM参数: -XX:+UseSerialGC -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *   -XX:-HandlePromotionFailure
     *   jdk1.6_u24及之后版本 会忽略HandlePromotionFailure参数, 默认开启 担保
     */
    public static void handlePormotionFailure(){
        byte[] allocation1,allocation2,allocation3,allocation4,allocation5,allocation6,allocation7;
        allocation1 = new byte[2*_1M];
        allocation2 = new byte[2*_1M];
        allocation3 = new byte[2*_1M];
        allocation1 = null;
        allocation4 = new byte[2*_1M];
        allocation5 = new byte[2*_1M];
        allocation6 = new byte[2*_1M];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2*_1M];
    }
}
