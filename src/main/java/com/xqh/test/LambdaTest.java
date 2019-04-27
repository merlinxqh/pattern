package com.xqh.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName LambdaTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/2/19 11:08
 * @Version 1.0
 */
public class LambdaTest {

    public static void main(String[] args) {
        //TODO 待办事项
//        getRemoveDuplicateList();
        groupByTest();
    }

    public static void groupByTest(){
        //FIXME
        List<TestDemo> list = getTestList();
        Map<String, List<TestDemo>> map = list.stream().collect(Collectors.groupingBy(TestDemo::getId));
        System.out.println(JSON.toJSONString(map));
    }

    public static List<TestDemo> getTestList(){
        List<TestDemo> list = Lists.newArrayList();
        list.add(TestDemo.builder()
                .id("id_1")
                .name("name_1")
                .build());
        list.add(TestDemo.builder()
                .id("id_1")
                .name("name_2")
                .build());
        list.add(TestDemo.builder()
                .id("id_2")
                .name("name_1")
                .build());
        list.add(TestDemo.builder()
                .id("id_3")
                .name("name_1")
                .build());
        return list;
    }

    /**
     * lambda 获取 去重集合
     * @return
     */
    public static List<TestDemo> getRemoveDuplicateList(){
        List<TestDemo> list = getTestList();
        List<TestDemo> resList = list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(()-> new TreeSet<>(Comparator.comparing(c-> c.getId()))), ArrayList::new
        ));
        System.out.println(JSON.toJSONString(resList));
        return resList;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class TestDemo{
      private String id;

      private String name;
    }
}
