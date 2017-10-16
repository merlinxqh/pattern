package com.xqh.juc.functional;

/**
 * Created by leo on 2017/10/16.
 */
public interface IDonkey {
    void eat();

    default void run(){
        System.out.println("Donkey run");
    }
}
