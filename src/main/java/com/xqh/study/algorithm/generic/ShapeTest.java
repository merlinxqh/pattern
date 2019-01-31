package com.xqh.study.algorithm.generic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 协变性 测试
 */
public class ShapeTest {

    public static double totalArea(Shape[] arr){
        double total = 0;
        for(Shape s:arr){
            total += s.area();
        }
        return total;
    }

    public static double totalArea(Collection<Shape> arr){
        double total = 0;
        for(Shape s:arr){
            total += s.area();
        }
        return total;
    }

    /**
     * 使用通配符 解决 集合 无协变性
     * @param arr
     * @return
     */
    public static double totalArea1(Collection<? extends Shape> arr){
        double total = 0;
        for(Shape s:arr){
            total += s.area();
        }
        return total;
    }

    public static void main(String[] args) {
        Square[] arr=new Square[1];
        arr[0] = new Square();
        /**
         * 数组具有协变性
         */
        double total = totalArea(arr);
        System.out.println(total);

        List<Shape> list = new ArrayList<>();
        totalArea(list);

        List<Square> slist= new ArrayList<>();
        slist.add(new Square());
        /**
         * 以下 编译不通过,  集合没有协变性
          */
//        totalArea(slist);
        System.out.println(totalArea1(slist));
    }

    public static class Shape{
        public double area(){
            return 10D;
        }
    }

    public static class Square extends Shape{

    }

    public static class Circle extends  Shape{

    }

}
