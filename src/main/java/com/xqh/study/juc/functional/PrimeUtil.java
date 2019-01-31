package com.xqh.study.juc.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by leo on 2017/10/16.
 * 统计1--1000000内所有的质数和数量
 *
 * 质数: 在大于1的自然数中 除了1 和 它本身 不会有其他 因数
 */
public class PrimeUtil {

    public static boolean isPrime(int number){
        int tmp = number;
        if(tmp < 2){
            return false;
        }
        //Math.sqrt 开方
        for (int i = 2; Math.sqrt(tmp) >= i;i++){
            if(tmp % i == 0){
                return false;
            }
        }
        System.out.println(number);
        return true;
    }

    public static void main(String[] args) {
        /**
         * 串行执行
         */
        IntStream.range(1,1000000).filter(PrimeUtil::isPrime).count();

        /**
         * 将流并行化
         */
        IntStream.range(1,1000000).parallel().filter(PrimeUtil::isPrime).count();


        /**
         * 从集合得到并行流
         */
        List<Student> ss=new ArrayList<>();

        double ave=ss.stream().mapToInt(s-> s.score).average().getAsDouble();

        //并行流
        double pave=ss.parallelStream().mapToInt(s->s.score).average().getAsDouble();


        int[] array=new int[10000];
        Random ran=new Random();
        Arrays.setAll(array,(i)-> ran.nextInt());
        Arrays.parallelSetAll(array,(i)->ran.nextInt());
        /**
         * 串行排序
         */
        Arrays.sort(array);
        /**
         * 并行排序
         */
        Arrays.parallelSort(array);
    }

    public static class Student{
        int score;
    }





}
