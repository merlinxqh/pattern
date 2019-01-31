package com.xqh.study.algorithm.recursive;

/**
 * 递归算法两个基准法则
 * 1. 基准情形 (base case). 必须总要有某些基准情形,它们不能递归就能求解.
 * 2. 不断推进(making process). 对于那些要递归求解的情形, 递归调用必须总能朝着一个基准情形推进.
 * 3. 设计法则(design rule).假设所有的递归调用都能正常运行.
 * 4. 合成效益法则(compound interest rule). 在求解一个问题的同一实例, 切勿在不同的递归调用中做重复性的工作.
 */
public class RecursiveTest {

    public static void main(String[] args) {
        printOut(123344555);
    }

    /**
     * 打印整数 所有 位 数
     *
     * ========== mod 非常耗时, 因为 n % 10 = n -[n/10]*10;
     * @param i
     */
    public static void printOut(int i){
       if(i >= 10){
           printOut(i/10);
       }
       printDigit(i%10);
    }

    /**
     * 打印
     * @param p
     */
    public static void printDigit(int p){
        System.out.println(p);
    }
}
