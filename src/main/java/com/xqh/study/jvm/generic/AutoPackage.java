package com.xqh.study.jvm.generic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class AutoPackage {
    /**
     * 展示
     * 泛型,自动装箱,自动拆箱, 遍历循环 与 变长参数   5种语法糖
     */
    public static void main1(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4);

        int sum = 0;
        for(int i : list){
            sum += i;
        }

        System.out.println(sum);

    }

    /**
     * 展示了上面例子在编译之后的变化.
     *   泛型Integer 被擦除
     *   自动装箱,自动拆箱在编译之后被转化为了 对应的包装和还原方法. 如 Integer.valueOf(), Integer.intValue()
     *   而遍历循环则是把代码 还原成了 迭代器 的实现, 这要是为何便利循环需要被便利的类实现Iterable接口的原因
     *   最后看看变长参数,它在调用的时候变成了一个数组来完成类似的功能.
     * @param args
     */
    public static void main2(String[] args) {
        List list = Arrays.asList(new Integer[]{Integer.valueOf(1),Integer.valueOf(2),
                Integer.valueOf(3),Integer.valueOf(4)});

        int sum = 0;
        for(Iterator localIterator = list.iterator(); localIterator.hasNext();){
            int i = ((Integer)localIterator.next()).intValue();
            sum += i;
        }

        System.out.println(sum);
    }

    /**
     * 查看AutoPackage.class 编译之后内容
     * @param args
     */
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == ( a+ b));
        System.out.println(c.equals(a+b));
        System.out.println(g == a+b);
        System.out.println(g.equals(a+b));
    }
}
