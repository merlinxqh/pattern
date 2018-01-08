package com.xqh.jvm.generic;

/**
 * 条件编译
 */
public class ConditionCompile {

    public static void main(String[] args) {
        if(1 == 1){
            System.out.println("condition1");
        }else{
            System.out.println("condition2");
        }

        //编译器拒绝编译以下代码
//        while(false){
//
//        }
    }
}
