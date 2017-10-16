package com.xqh.juc.functional;

import java.util.Arrays;

/**
 * Created by leo on 2017/10/13.
 */
public class DeclarativeDemo {

    static int[] arry={1,4,2,6,12,3,90,11,21,16};


    /**
     * 命令式编程
     */
    public static void imperative(){
        int[] iArr={1,4,2,6,12,3,90,11,21,16};
        for(int i=0;i<iArr.length;i++){
            System.out.println(iArr[i]);
        }
    }

    /**
     * 神明式代码
     */
    public static void declarative(){
        int[] iArr={1,4,2,6,12,3,90,11,21,16};
        Arrays.stream(iArr).forEach(System.out::println);
    }

    public static void main(String[] args) {
//        imperative();
//        declarative();
        /**
         * 在使用函数式编程时,几乎所有的对象都拒绝被修改. 类似 不变模式
         */
//        Arrays.stream(arry).map((x) -> x=x+1).forEach(System.out::println);
//        System.out.println();
//        Arrays.stream(arry).forEach(System.out::println);

//        for(int i=0;i<arry.length;i++){
//            if(arry[i]%2 != 0){
//                arry[i]++;
//            }
//            System.out.println(arry[i]);
//        }
        Arrays.stream(arry).map(x-> x%2==0?x:x+1).forEach(System.out::println);
    }

    @FunctionalInterface
    public static interface InHandler {
        void handle(int i);
    }
}
