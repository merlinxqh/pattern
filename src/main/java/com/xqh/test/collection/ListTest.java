package com.xqh.test.collection;

import com.alibaba.fastjson.JSON;
import com.xqh.test.Test;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Stream;

/**
 * @ClassName ListTest
 * @Description {@link java.util.ArrayList}
 * @Author xuqianghui
 * @Date 2019/3/27 17:48
 * @Version 1.0
 */
public class ListTest {

    public static void main(String[] args) {
//        wrongRemoveTest();
//        String[] arry = "asdf,asdfgg,".split(",");
//        Stream.of(arry).forEach(f->{
//            System.out.println("++++>"+f);
//        });
        foreachTest();
    }

    public static boolean foreachTest(){
        for(int i = 0;i <3; i++){
            for(int j = 0; j<5;j++){
                if(j == 3){
                    return false;
                }
                System.out.println("======> "+ j);
            }
        }
        return true;
    }




    public static void sortTest(){
        List<TestDemo> list = Arrays.asList(
                TestDemo.builder()
                        .id(3)
                        .name("天道酬勤")
                        .build(),
                TestDemo.builder()
                        .id(1)
                        .name("何乐而不为")
                        .build(),
                TestDemo.builder()
                        .id(2)
                        .name("正式")
                        .build()
        );
        List<String> testList = Arrays.asList("王道", "天地", "人间正气", "美国霸权够");
        testList.sort(Comparator.comparing(String::toString));
        System.out.println(JSON.toJSONString(testList));
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestDemo{
        private int id;

        private String name;
    }

    /**
     * 错误的集合remove方法
     */
    public static void wrongRemoveTest() {
        List<String> list = new ArrayList<>(2);
        list.add("1");
        list.add("2");
        for (String str : list) {
            if ("1".equals(str)) {//换成 "2" 结果不一样
                list.remove(str);
            }
        }
    }

    /**
     * 正确的集合remove
     */
    public static void removeTest(){
        List<String> list = new ArrayList<>(2);
        list.add("1");
        list.add("2");
        Iterator<String> iterator =list.iterator();
        while (iterator.hasNext()){
            String item = iterator.next();
            if("1".equals(item)){
                iterator.remove();
            }
        }
    }
}
