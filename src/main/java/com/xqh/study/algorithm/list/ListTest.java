package com.xqh.study.algorithm.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListTest {


    /**
     * 不管是ArrayList 或者 LinkedList作为参数
     * makeList1()方法 运行时间都是 0(N)
     * 因为对add的每次调用都是在表的末端进行, 从而均花费常熟时间.
     * @param list
     * @param n
     */
    public static void makeList1(List<Integer> list, int n){
        list.clear();
        for(int i = 0;i<n;i++)
            list.add(i);
    }

    /**
     * 每次通过在表的前端添加一些项来构造一个list
     * 那么对于 LinkedList 它的运行时间是 0(N), 但是对于ArrayList 其运行时间则是 0(N²)
     * 因为在 ArrayList中, 在前端添加时一个 0(N)操作.
     * @param list
     * @param n
     */
    public static void makeList2(List<Integer> list, int n){
        list.clear();
        for(int i=0;i<n;i++)
            list.add(0, i);
    }

    /**
     * 这里 对于 ArrayList 来说 运行时间是 0(N),
     * 但是对于 LinkedList 来说 运行时间是 0(N²)
     *   因为在 LinkedList中 get 的调用 是0(N)操作.
     *
     * @param list
     * @return
     */
    public static int sum(List<Integer> list){
        int total = 0;
        for(int i = 0; i< list.size(); i++)
            total += list.get(i);
        return total;
    }


    /**
     * 删除 表中元素
     * 对于 ArrayList remove的效率 不高, 因此该程序花费的时间是二次 时间
     * 对于 LinkedList get调用的效率不高, 因此例程 花费二次时间. 而且 对于remove 的调用同样低效, 因为达到位置 i 的代价很高.
     * @param lst
     */
    public static void removeEvensVer1(List<Integer> lst){
        int i = 0;
        while (i < lst.size()){
            if(lst.get(i) % 2 == 0){
                lst.remove(i);
            }else
                i++;
        }
    }


    /**
     * 我们不用 get方法, 而是使用 迭代器 一步一步遍历该表, 这是高效的. 但是 我们使用 Collection 的 remove方法删除一个偶数项,
     * 这不是 高效的操作, 因为remove方法必须再次搜索该项 , 花费线性时间.
     *  而且这个程序 运行会报错
     * 因为 增强for循环 是使用 迭代器实现的. 迭代循环进行表的 删除 添加操作 都是 非法的.
     * @param list
     */
    public static void removeEvensVer2(List<Integer> list){
        for(Integer i:list){
            if(i % 2 == 0)
                list.remove(i);
        }
    }

    /**
     * 在迭代器找到一个偶数项后, 我们可以使用该迭代器来删除这个它刚看到的值.
     *  对于一个 LinkedList, 对该迭代器的 remove 方法调用只花费常数时间. 因为该迭代器位于需要被删除的节点(或在其附近), 因此 对于整个LinkedList ,
     *    整个程序话费线性时间. 而不是二次时间.
     *  对于一个 ArrayList ,即使迭代器位于需要被删除的节点上, 但是remove方法仍然是昂贵的 . 因为数组必须要移动, 所以需要话费 二次时间.
     * @param list
     */
    public static void removeEvensVer3(List<Integer> list){
        Iterator<Integer> it = list.iterator();

        while(it.hasNext()){
            if(it.next() % 2 == 0)
                it.remove();
        }
    }

    public static void main(String[] args) {

        /**
         * ArrayList
         * 400000 耗时 5071
         * 800000 耗时 20848 是 二次时间
         */
//        List<Integer> list = getArrayList(800000);

        /**
         * LinkedList
         * 400000 耗时 12
         * 800000 耗时 16  线性时间
         */
        List<Integer> list = getLinkedList(800000);
        long start = System.currentTimeMillis();
        removeEvensVer3(list);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static List<Integer> getArrayList(int size){
       List<Integer> list = new ArrayList<>();
       for(int i=0;i<size;i++){
           list.add(i);
       }

       return list;
    }

    public static List<Integer> getLinkedList(int size){
        List<Integer> list = new LinkedList<>();
        for(int i=0;i<size;i++){
            list.add(i);
        }
        return list;
    }
}
