package com.xqh.juc.functional.methodRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/10/16.
 * 构造方法引用
 */
public class ConstructMethodRef {

    @FunctionalInterface
    interface  UserFactory<U extends User>{
        U create(int id,String name);
    }

    static UserFactory<User> uf=User::new;

    public static void main(String[] args) {
        List<User> users=new ArrayList<>();
        for(int i=0;i<10;i++){
            users.add(uf.create(i,"billy"+Integer.toString(i)));
        }
        users.stream().map(User::getName).forEach(System.out::println);
    }
}
