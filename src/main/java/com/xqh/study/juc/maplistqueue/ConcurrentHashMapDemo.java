package com.xqh.study.juc.maplistqueue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by leo on 2017/9/28.
 * 线程安全hashMap
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        /**
         * ConcurrentHashMap细分为16段
         * 根据hash算法 得到 key属于哪一段 , 对该段做加锁处理
         * 减少锁粒度
         */
        Map<String,Object> map=new ConcurrentHashMap<>();

        System.out.println(1 << 30);

        ConcurrentLinkedQueue<String> qu=new ConcurrentLinkedQueue<>();

        BlockingQueue<String> bq=new LinkedBlockingQueue<>();

        ThreadLocal<String> local=new ThreadLocal<>();

    }
}
