package com.xqh.study.juc.functional.methodRef;

/**
 * Created by leo on 2017/10/16.
 */
public class User{
    private int id;

    private String name;

    public User(){

    }

    public static long getTest(){
        return System.currentTimeMillis();
    }

    public User(int id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
