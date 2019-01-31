package com.xqh.study.juc.design.future.jdkFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by leo on 2017/10/10.
 */
public class FutureMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //构造FutureTask
        FutureTask<String> future= new FutureTask<String>(new RealData("a"));
        ExecutorService exe= Executors.newFixedThreadPool(1);
        //执行futureTask, 相当于上个例子 client.request("abc")发送请求
        exe.submit(future);
        System.out.println("请求完毕");
        //执行其他业务
        Thread.sleep(2000);
        System.out.println("realData="+future.get());
        /**
         * JDK的Future接口提供了一些简单的控制接口
         * boolean cancel(boolean mayInterruptIfRunning); //取消任务
         * boolean isCanceled();                          //是否已经取消
         * boolean isDone();                              //是否已经完成
         * V get() throws InterruptedException, ExecutionException //取得返回对象
         * V get(long timeout, TimeUnit unit);            //取得返回对象可以设置超时时间
         */

    }
}
