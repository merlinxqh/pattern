package com.xqh.imooc.highconcurrency;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 多线程 并发访问map
 */
@Slf4j
public class MapCountExample {

    private static int totalThread = 200;

    private static int totalClient = 5000;

    private static Map<Integer, Integer> map = Maps.newHashMap();

    public static void main(String[] args) {
        ExecutorService exce = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(totalThread);
        for(int i = 0; i<totalClient;i++){
            final int threadNum = i;
            exce.execute(()->{
                try {
                    semaphore.acquire();
                    func(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            });
        }
        exce.shutdown();
        log.info("size={}", map.size());
    }

    public static void func(int threadNum){
        map.put(threadNum, threadNum);
    }
}
