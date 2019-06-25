package com.xqh.test.pool;

import com.xqh.utils.ThreadPoolUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName ThreadPoolTest
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/6/24 14:34
 * @Version 1.0
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++){
            FutureTask<Integer> future = new FutureTask<>(new TestThread(i));
            ThreadPoolUtils.submit(future);
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    public static class TestThread implements Callable<Integer> {

        private int idx;


        public TestThread(int idx){
            this.idx = idx;
        }

        @Override
        public Integer call() throws Exception {
            if(idx < 20){
                System.out.println("less than 20....");
            }else{
                System.out.println("great than 20....");
            }
            if(idx < 20 && idx % 2 == 0){
                FutureTask<Integer> future = new FutureTask<>(new TestThread(idx*100));
                ThreadPoolUtils.submit(future);
                System.out.println(future.get());
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return idx*5;
        }
    }
}
