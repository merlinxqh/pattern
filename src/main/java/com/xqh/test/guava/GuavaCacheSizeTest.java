package com.xqh.test.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName GuavaCacheSizeTest
 * @Description guava缓存 size 测试
 * @Author xuqianghui
 * @Date 2019/6/24 13:56
 * @Version 1.0
 */
public class GuavaCacheSizeTest {

    public static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5)
//            .refreshAfterWrite(3, TimeUnit.SECONDS)// 三秒刷新数据, 可以先获取旧的缓存值 返回.
            .expireAfterWrite(200, TimeUnit.SECONDS) // 3秒刷新数据, 所有线程阻塞等待 获取 刷新后的值
//            .expireAfterAccess(10, TimeUnit.SECONDS) // 限定时间内 没有 访问 该数据, 会 执行异步 刷新缓存数据方法.
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return s.concat("_value");
                }
            });

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            cache.getUnchecked("test_key_"+i);
            System.out.println(JSON.toJSONString(cache.asMap()));
        }
    }
}
