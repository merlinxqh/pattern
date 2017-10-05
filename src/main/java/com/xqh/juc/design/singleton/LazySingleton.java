package com.xqh.juc.design.singleton;

/**
 * Created by leo on 2017/10/5.
 */
public class LazySingleton {

    private LazySingleton(){
        System.out.println("lazySingleton is create...");
    }

    private static LazySingleton instance=null;

    public synchronized static LazySingleton getInstance(){
        if(instance == null)
            instance=new LazySingleton();
        return instance;
    }
}
