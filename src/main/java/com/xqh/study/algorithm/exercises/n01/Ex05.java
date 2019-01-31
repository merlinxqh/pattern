package com.xqh.study.algorithm.exercises.n01;

/**
 * 练习题1.5 编写一个递归算法,它返回数 N 的 二进制数 1 的个数.
 *    利用这样的事实,  如果 N 是奇数,那么其 1 的个数 是 N/2 二进制表示中 1 的个数加1
 */
public class Ex05 {

    public static void main(String[] args) {
       int num = 12819;
        System.out.println(num+" 二进制表示为: "+Integer.toBinaryString(num));
        System.out.println("其二进制表示包含1的个数为: "+getNum1Count(num));

        if(num % 2 != 0){
            int half = num/2;
            System.out.println(half+" 二进制表示为:"+Integer.toBinaryString(half));
            System.out.println("其二进制表示包含1的个数为: "+getNum1Count(half));
        }
    }

    public static int getNum1Count(Integer  num){
        String bin = Integer.toBinaryString(num);
        int i = 0;
        for(char c:bin.toCharArray()){
            if("1".equals(String.valueOf(c))){
                i++;
            }
        }
        return i;
    }
}
