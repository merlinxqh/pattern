package com.xqh.juc.functional;

/**
 * Created by leo on 2017/10/16.
 */
public interface IAnimal {

    default void breath(){
        System.out.println("breath");
    }
}
