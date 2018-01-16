package com.xqh.algorithm.generic;

import java.util.Comparator;

/**
 * 类型界定 type bound
 */
public class TypeBoundTest {

    /**
     * 假设 Shape实现Comparable<Shape> , 设 Square 继承 Shape ,
     * 此时 我们知道 Square 实现 Comparable<Shape> 于是 Square IS-A Comparable<Shape>
     * 但是 Square IS-NOT-A Comparable<Square>
     * @param arr
     * @param <AnyType>
     * @return
     */
    public static <AnyType extends Comparable<? super AnyType>> AnyType findMax(AnyType[] arr){
//        AnyType a = new AnyType();//非法
//        AnyType[] aa=new AnyType[12];//非法
        int maxIdx = 0;
        for(int i=0; i < arr.length; i++){
            if(arr[i].compareTo(arr[maxIdx]) > 0){
                maxIdx = i;
            }
        }
        return arr[maxIdx];
    }

    /**
     * 函数对象
     * @param arr
     * @param cmp
     * @param <AnyType>
     * @return
     */
    public static <AnyType> AnyType  findMax(AnyType[] arr, Comparator<? super AnyType> cmp){
        int maxIdx = 0;
        for(int i=0; i < arr.length; i++){
            if(cmp.compare(arr[i], arr[maxIdx]) > 0){
                maxIdx = i;
            }
        }
        return arr[maxIdx];
    }

    static class CaseInsensitiveCompare implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"ZEBRA", "alligator", "crocodile"};
        System.out.println(findMax(arr,new CaseInsensitiveCompare()));
    }
}
