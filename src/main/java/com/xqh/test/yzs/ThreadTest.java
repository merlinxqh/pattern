package com.xqh.test.yzs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ThreadTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/8/31 12:16
 * @Version 1.0
 */
@Slf4j
public class ThreadTest {

    private static boolean is_success = false;

    private static AtomicInteger atomic = new AtomicInteger(0);

    public static void main(String[] args) {
        new ThreadTest().testExecute();
    }

    public void testExecute() {
        log.info("{} is executing..", Thread.currentThread().getName());
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testExecute();
        }, "test_thread_" + atomic.incrementAndGet()).start();

    }
}
