package com.xqh.study.jvm;
import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;


/**
 * 方法调用问题
 */
class MethodInvokeTest {
    class GrandFather{
        void thinking(){
            System.out.println("i am grandfather");
        }
    }

    class Father extends GrandFather{
        void thinking(){
            System.out.println("i am father");
        }
    }

    class Son extends Father{
        void thinking(){
            //加入适当代码, 不修改其他地方的代码, 实现 调用祖父类的thinking()方法
            try {
                MethodType mt=MethodType.methodType(void.class);
                MethodHandle mh=lookup().findSpecial(GrandFather.class,"thinking",mt,getClass());
                mh.invoke(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new MethodInvokeTest().new Son()).thinking();
    }
}
