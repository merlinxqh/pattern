package com.xqh.test.wildcard;

import com.alibaba.fastjson.JSON;
import com.xqh.study.pattern.partterns.abstractFactory.App;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WildcardTest
 * @Description 通配符测试
 * @Author xuqianghui
 * @Date 2019/3/27 18:33
 * @Version 1.0
 */
public class WildcardTest {

    public static void main(String[] args) {
        downBoundWildcard();
    }

    /**
     * 下届通配符 测试
     * <? super T>
     */
    public static void downBoundWildcard() {
        Plate<? super Fruit> plate = new Plate<>(new Apple());
        System.out.println(JSON.toJSONString(plate.getItem()));

    }

    /**
     * 上届 通配符测试
     * <? extends T>
     */
    public static void upBoundWildcard() {
        Plate<? extends Fruit> plate = new Plate<Apple>(new Apple());
       Fruit newFruit =  plate.getItem();//能调用 get方法
//        Apple newFruit2 = plate.getItem();// error
        Object newFruit3 = plate.getItem();
//        plate.setItem(new Apple())// error

    }


    /**
     * 实物基类
     */
    @Data
    public static class Food {

    }

    /**
     * 肉类
     */
    @Data
    public static class Meat extends Food {

    }

    /**
     * 水果基类
     */
    @Data
    public static class Fruit extends Food {

    }

    @Data
    public static class Apple extends Fruit {

    }

    /**
     * 盘子
     *
     * @param <T>
     */
    @Data
    public static class Plate<T> {
        private T item;

        public Plate(T t) {
            item = t;
        }
        public Plate(){

        }
    }
}
