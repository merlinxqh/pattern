package com.xqh.test.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.xqh.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.callback.Callback;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName GuavaCacheTest
 * @Description guava 缓存测试
 * @Author xuqianghui
 * @Date 2019/5/14 13:29
 * @Version 1.0
 */

/**
 * guava cache 适用场景
 * 1. 你愿意消耗一些内存空间来提升速度。
 * 2. 你预料到某些键会被查询一次以上。
 * 3. 缓存中存放的数据总量不会超出内存容量。（Guava Cache是单个应用运行时的本地缓存。
 *    它不把数据存放到文件或外部服务器。如果这不符合你的需求，请尝试Memcached这类工具）
 */
@Slf4j
public class GuavaCacheTest {


    public final static String cache_key = "cacheKey";
    /**
     * guava cache test
     */
    public static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000)
//            .refreshAfterWrite(3, TimeUnit.SECONDS)// 三秒刷新数据, 可以先获取旧的缓存值 返回.
            .expireAfterWrite(3, TimeUnit.SECONDS) // 3秒刷新数据, 所有线程阻塞等待 获取 刷新后的值
//            .expireAfterAccess(10, TimeUnit.SECONDS) // 限定时间内 没有 访问 该数据, 会 执行异步 刷新缓存数据方法.
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    FutureTask<String> future = new FutureTask<>(new AsyncLoadThread());
                    new Thread(future).start();
                    return future.get();
                }
            });

    public static class AsyncLoadThread implements Callable<String>{
        @Override
        public String call() throws Exception {
            String ret = UUID.randomUUID().toString();
            System.out.println("开始load 数据, new cache value ========>"+ret);
            Thread.sleep(3000);
            return ret;
        }
    }

    public static class ReadCacheThread implements Runnable{

        @Override
        public void run() {
            String cacheVal = getCacheVal();
            System.out.println(Thread.currentThread().getName()+" get cache val==> "+cacheVal);
        }
    }

    public static void main(String[] args) {
        AtomicInteger idx = new AtomicInteger(0);
        int j=0;
        while (true){
            j++;
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if(j == 10){
//                try {
//                    Thread.sleep(20000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            for(int i = 0; i<2; i++){
                new Thread(new ReadCacheThread(), "read-thread-"+idx.incrementAndGet()).start();
            }
        }
    }

    /**
     * 获取缓存内容
     * @return
     */
    public static String getCacheVal(){
//        RateLimiter limiter = RateLimiter.create(10);
//        if(limiter.tryAcquire(3, TimeUnit.SECONDS)){
//        }
        return cache.getUnchecked(cache_key);
    }


}
