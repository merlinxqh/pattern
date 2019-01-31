package com.xqh.study.juc.functional.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by leo on 2017/10/17.
 */
public class CompletableFutureDemo {

    public static void main1(String[] args) {
        CompletableFuture stage=new CompletableFuture();

        /**
         * 一连串调用 挨个执行
         */
        stage.thenApply(x-> square(x)).thenAccept(x -> System.out.println(x)).thenRun(() -> System.out.println());
    }


    public static Object square(Object x){
        return x;
    }


    public static void main2(String[] args) throws InterruptedException {

        /**
         * 完成了就通知我
         */
        final CompletableFuture<Integer> future=new CompletableFuture<>();
        new Thread(new AskThread(future)).start();
        //模拟长时间计算过程
        Thread.sleep(1000);
        //告知结果
        future.complete(60);
    }


    public static class AskThread implements Runnable{
        CompletableFuture<Integer> re=null;

        public AskThread(CompletableFuture<Integer> re){
            this.re=re;
        }
        @Override
        public void run() {
            int myRe=0;
            try {
                myRe=re.get()*re.get();//阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(myRe);
        }
    }


    public static void main3(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 异步执行任务
         * supplyAsync()函数中,它会在一个新的线程中,执行传入的参数.
         * 在这里 它会执行 calc()方法. 而calc()方法执行可能比较慢
         * 但是并不影响CompletableFuture实例的构造速度. 因此supplyAsync()会立即返回
         * 调用future.get()会线程等待 执行结果.
         *
         *
         * 在CompletableFuture中,类似的工厂方法还有
         * runAsync() //没有返回值 仅仅是简单执行某一个异步动作.
         * supplyAsync()和runAsync()方法 都可以接受一个指定的Executor参数,
         * 在指定的线程池中工作,如果不指定就是默认的系统公共的ForkJoinPool.common线程池中执行.
         */
        final CompletableFuture<Integer> future=CompletableFuture.supplyAsync(() -> calc(50));
        //TODO another things...
        System.out.println(future.get());//get result

    }

    public static void main4(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 流式调用
         */
        CompletableFuture<Void> fu=CompletableFuture.supplyAsync(() -> calc(51))
                .thenApply((i)-> Integer.toString(i))
                .thenApply((str) -> "\""+str+"\"")
                .thenAccept(System.out::println);

        fu.get();
    }

    public static void main5(String[] args) throws ExecutionException, InterruptedException {
        /**
         * CompletableFuture中的异常处理
         */
        CompletableFuture<Void> fu=CompletableFuture.supplyAsync(() -> calc2(51))
                .exceptionally(ex -> {
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply((i)-> Integer.toString(i))
                .thenApply((str) -> "\""+str+"\"")
                .thenAccept(System.out::println);

        fu.get();
    }

    public static Integer calc2(Integer para){

        return para / 0;
    }

    public static Integer calc(Integer para){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para*para;
    }


    public static void main6(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 组合多个CompletableFuture
         * 一个CompletableFuture可以在执行完成之后,将执行结果通过Function传递给下一个CompletableFuture进行处理
         * (Function接口返回的是新的CompletableFuture实例)
         */
        CompletableFuture<Void> fu=CompletableFuture.supplyAsync(()-> calc3(50))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc3(i)))
                .thenApply((str) -> "\""+str+"\"").thenAccept(System.out::println);
        fu.get();
    }

    public static int calc3(int para){
        return para/2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 方法thenCombine()首先完成当前的CompletableFuture和other的执行. 接着 ,接着将这两者的执行结果
         * 传递给BiFunction (该接口接收两个参数,并有一个返回值), 并返回代表BiFunction实例的CompletableFuture对象.
         */
        CompletableFuture<Integer> intFuture=CompletableFuture.supplyAsync(() -> calc3(50));
        CompletableFuture<Integer> intFuture2=CompletableFuture.supplyAsync(() -> calc3(25));
        CompletableFuture<Void> fu=intFuture.thenCombine(intFuture2, (i,j) -> (i+j))
                .thenApply((str) -> "\""+str+"\"")
                .thenAccept(System.out::println);
        fu.get();
    }
}
