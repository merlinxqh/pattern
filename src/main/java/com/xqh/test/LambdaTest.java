package com.xqh.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
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
        getRemoveDuplicateList();
    }

    /**
     * lambda 获取 去重集合
     * @return
     */
    public static List<TestDemo> getRemoveDuplicateList(){
        List<TestDemo> list = Lists.newArrayList();
        list.add(TestDemo.builder()
                .id("id_1")
                .name("name_1")
                .build());
        list.add(TestDemo.builder()
                .id("id_1")
                .name("name_1")
                .build());
        list.add(TestDemo.builder()
                .id("id_2")
                .name("name_1")
                .build());
        list.add(TestDemo.builder()
                .id("id_3")
                .name("name_1")
                .build());
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
