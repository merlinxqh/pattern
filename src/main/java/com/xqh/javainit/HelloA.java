package com.xqh.javainit;

/**
 * Created by leo on 2018/2/26.
 */
public class HelloA {
    {
        System.out.println("object A");
    }

    static{
        System.out.println("class A");
    }

    public HelloA(){
        System.out.println("HelloA");
    }


    public static class HelloB extends HelloA{
        {
            System.out.println("object B");
        }

        static {
            System.out.println("class B");
        }

        public HelloB(){
            System.out.println("HelloB");
        }
    }

    public static void main(String[] args) {
        new HelloB();
    }
}
