package com.xqh.study.juc.functional;

import java.util.Comparator;

/**
 * Created by leo on 2017/10/16.
 */
public class Mule implements IHorse,IAnimal,IDonkey {
    @Override
    public void run() {
        IHorse.super.run();//也可以自己实现
    }

    @Override
    public void eat() {
        System.out.println("Mule eat");
    }

    public static void main(String[] args) {
        Mule mule=new Mule();
        mule.breath();
        mule.run();
        Comparator<String> tmp=Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER);
    }
}
