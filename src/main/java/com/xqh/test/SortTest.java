package com.xqh.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

/**
 * @ClassName SortTest
 * @Description 排序 测试类
 * @Author xuqianghui
 * @Date 2019/2/23 11:19
 * @Version 1.0
 */
public class SortTest {

    public static void main(String[] args) {
        List<SortDemo> list = Lists.newArrayList();
        list.add(SortDemo.builder()
                .order(3)
                .name("3333")
                .build());
        list.add(SortDemo.builder()
                .order(1)
                .name("一一一")
                .build());
        list.add(SortDemo.builder()
                .order(2)
                .name("2222")
                .build());
        list.sort(Comparator.comparingInt(SortDemo::getOrder));
        System.out.println(JSON.toJSONString(list));
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortDemo{

        private int order;

        private  String name;
    }
}
