package com.xqh.juc.design.singleton;

/**
 * Created by leo on 2017/10/5.
 * 结合以上两种优势 的单例模式
 */
public class StaticSingleton {
    private StaticSingleton(){
        System.out.println("staticSingleton is create...");
    }

    private static class SingletonHolder{
        private static StaticSingleton instance=new StaticSingleton();
    }

    public static StaticSingleton getInstance(){
        return SingletonHolder.instance;
    }
}
