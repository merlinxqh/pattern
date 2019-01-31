package com.xqh.study.juc.design.notChange;

/**
 * Created by leo on 2017/10/5.
 * 不变模式
 * 1. 当对象创建后,其内部的状态和数据不再发生任何变化.
 * 2. 对象需要被共享,被多线程平凡访问.
 *
 * 在Java语言中,不变模式的实现很简单. 为确保对象创建之后不发生任何改变,并保证不变模式正常工作, 只需要注意一下四点:
 * 1. 去除setter方法以及所有修改自身属性的方法.
 * 2. 将所有属性设为私有,并用final标记,确保其不可修改.
 * 3. 确保没有子类可以重载修改他的行为.
 * 4. 有一个可以创建完整对象的构造函数.
 *
 *
 * 在JDK中,不变模式应用广泛. 其中最典型的就是java.lang.String类,此外 所有的元数据包装类, 都是使用不变模式实现的.
 * String, Boolean, Double, Byte, Character, Float, Integer, Long, Short
 *
 *
 * 注意:
 *   不变模式通过回避问题而不是解决问题的态度来处理多线程并发访问控制.
 *   不变对象不需要进行同步操作. 由于并发同步会对性能产生不良影响, 因此
 *   在需求允许的情况下,不变模式可以提高系统的并发性能和并发量.
 */

public final class Product {                        // final 确保没有子类
    /**
     * final 保证属性不可二次赋值,但是属性内容可以修改
     */
    public static final TestObj obj=new TestObj("finalName","finalTitle");

    private final String no;                        //私有属性,不会被其他对象获取.

    private final String name;                      //final 保证属性不会被二次赋值

    private final double price;


    public Product(String no, String name, double price) {//在创建对象时必须指定数据,因为创建之后无法修改.
        super();
        this.no = no;
        this.name = name;
        this.price = price;
    }

    /**
     * 没有setter方法
     * @return
     */
    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static void main(String[] args) {

    }

    public static class TestObj{
        private String name;
        private String title;

        public TestObj(String name,String title){
            this.name=name;
            this.title=title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
