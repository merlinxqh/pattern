package com.xqh.study.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by leo on 2018/1/2.
 */
public class MethodHandleTest {
    static class ClassA{
        public void println(String s,Integer i){
            System.out.println("String:"+s+", Integer:"+i);
        }

        public void println(String s){
            System.out.println("just print:"+s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj=new ClassA();
        /**
         * 无论obj最终是哪个实现类,下面这句都能正确调用到println方法
         */
        getPrintInWH(obj).invokeExact("icyfenix");
    }

    private static MethodHandle getPrintInWH(Object reveiver) throws Throwable{
        /**
         * MethodType: 代表"方法类型",包含了方法的返回值(methodType()的第一个参数)
         * 和具体参数(methodType()第二个及以后的参数)
         */
        MethodType mt=MethodType.methodType(void.class, String.class);
        /**
         * lookup()方法来自于MethodHandles.lookup,这句的作用是在指定类中查找符合
         *  给定的方法名称,方法类型, 并且符合调用权限的 方法句柄
         */
        /**
         * 因为这里调用的是一个虚方法,按照java语言的规则, 方法第一个参数是隐式的, 代表该方法的接收者,
         * 也即是this指向的对象, 这个参数以前是放在参数列表中进行传递的,而现在提供了bindTo()方法来完成这件事.
         */
        return lookup().findVirtual(reveiver.getClass(), "println", mt).bindTo(reveiver);
    }



}
