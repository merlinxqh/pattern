package com.xqh.study.javainit;

/**
 * Created by leo on 2018/2/26.
 */
public class People {

    String name;

    public People(){
        System.out.println(1);
    }

    public People(String name){
        System.out.println(2);
        this.name = name;
    }

    public static void main(String[] args) {
        new Child("mike");
    }

}
class Child extends People{
    People father;

    public Child(String name){
       System.out.println(3);
       this.name = name;
       father = new People(name + ".F");

    }

    public Child(){
        System.out.println(4);
    }
}
