package com.xqh.study.juc.functional.methodRef;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<UserDto> res=users.stream().map(user-> copyObj(user)).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(res));
    }

    public static UserDto copyObj(User user){
        return JSON.parseObject(JSON.toJSONString(user),UserDto.class);
    }
public static class UserDto{
    private int id;

    private String name;

    public UserDto(){

    }

    public static long getTest(){
        return System.currentTimeMillis();
    }

    public UserDto(int id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
}

