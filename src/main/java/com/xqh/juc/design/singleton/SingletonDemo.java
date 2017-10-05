package com.xqh.juc.design.singleton;

/**
 * Created by leo on 2017/10/5.
 */
public class SingletonDemo {

    private SingletonDemo(){
        System.out.println("singleton instance create...");
    }

    /**
     * 该单例对象 会在类的第一次初始化 时创建
     */
    private static SingletonDemo instance=new SingletonDemo();

    public static SingletonDemo getInstance(){
        return instance;
    }
}
