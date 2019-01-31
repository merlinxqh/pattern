package com.xqh.study.juc.functional.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;

/**
 * Created by leo on 2017/10/16.
 */
public class LambdaDemo {

    static int[] arr={1,2,3,4,5,6,7,8,9,10};
    public static void main(String[] args) {
        List<Integer> numbers= Arrays.asList(1,2,3,5,7,12,33);
        numbers.forEach((Integer value)-> System.out.println(value));

        /**
         * 和匿名对象一样,lambda表达式可以访问外部的局部变量.
         * 变量必须申明为 final,这样才能在lambda中合法的访问它.
         * 这里 你不用final也能编译通过,并且正确输出6, 这其实是java8做的一个掩人耳目的小处理,
         * 它会自动地将在lambda中使用的变量视为final.
         */

        final int num=2;

        Function<Integer,Integer> stringConverter= (from) -> from*num;
        System.out.println(stringConverter.apply(3));

        IntConsumer outPrintLn=System.out::println;
        IntConsumer errorPrintLn=System.out::println;
        Arrays.stream(arr).forEach(outPrintLn.andThen(errorPrintLn));
    }
}
