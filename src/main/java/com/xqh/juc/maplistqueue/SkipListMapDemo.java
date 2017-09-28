package com.xqh.juc.maplistqueue;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by leo on 2017/9/28.
 * 随机数据结构 调表 SkipListMap
 */
public class SkipListMapDemo {
    public static void main(String[] args) {
        Map<Integer,Integer> map=new ConcurrentSkipListMap<>();
        for(int i=0;i<30;i++){
            map.put(i,i);
        }
        /**
         * 调表遍历 是有序的
         */
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
