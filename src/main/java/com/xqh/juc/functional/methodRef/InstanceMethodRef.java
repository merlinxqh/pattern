package com.xqh.juc.functional.methodRef;

import com.xqh.juc.functional.DeclarativeDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/10/16.
 */
public class InstanceMethodRef {
    /**
     * 方法引用
     * 方法引用是java8中提出的用来简化lambda表达式的一种手段.
     * 它通过类名和方法名来定位到一个静态方法或者实例方法.
     * 1. 静态方法引用 ClassName::methodName
     * 2. 实例上的实例方法引用: instanceReference::methodName
     * 3. 超类上的实例方法引用: super::methodName
     * 4. 类型上的实例方法引用: ClassName::methodName
     * 5. 构造方法引用: Class::new
     * 6. 数组构造方法引用: TypeName[]::new
     */
    public static void main(String[] args) {
        List<User> users=new ArrayList<>();
        for(int i=0;i<10;i++){
            users.add(new User(i,"billy"+Integer.toString(i)));
        }
        users.stream().map(User::getName).forEach(System.out::println);
    }
}
