package com.xqh.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 2017/9/23.
 */
public class DivTask implements Runnable {
    int a,b;

    public DivTask(int a,int b){
        this.a=a;
        this.b=b;
    }

    @Override
    public void run() {
       double re=a/b;
        System.out.println(re);
    }

    public static void main(String[] args) {

//        ExecutorService exe=new ThreadPoolExecutor(0,Integer.MAX_VALUE,0L, TimeUnit.MILLISECONDS,
//                new SynchronousQueue<>());
        ExecutorService exe=new TraceThreadPoolExecutor(0,Integer.MAX_VALUE,0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());
        for(int i=0;i<5;i++){
            DivTask task=new DivTask(100,i);
            /**
             * 使用execute(task);//会打印错误堆栈
             * 或者Future re=exe.submit(task);
             * re.get();
             */
            exe.submit(task);//
        }
    }
}
