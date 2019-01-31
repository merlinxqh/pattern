package com.xqh.study.jvm.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericTypes {

    /**
     * 以下情况不能编译, 因为参数List<String> ,List<Integer> 编译之后类型被擦除了
     * 变成一样的原生类型 List<E>, 擦除动作 导致这两种方法的特征签名一模一样
     */
//    public static void method(List<String> list){
//        System.out.println("method(List<String> list)");
//    }
//
//    public static void method(List<Integer> list){
//        System.out.println("method(List<Integer> list)");
//    }


    /**
     * 以下两种 又能编译通过, 因为是 有不同的返回类型
     * @param list
     * @return
     */
    public static String method1(List<String> list){
        System.out.println("method(List<String> list)");
        return "";
    }

    public static int method(List<Integer> list){
        System.out.println("method(List<Integer> list)");
        return 1;
    }

    public static void main(String[] args) {
        method1(new ArrayList<String>());
        method(new ArrayList<Integer>());
    }
}
