package com.xqh.juc.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Created by leo on 2017/10/16.
 * 统计1--1000000内所有的质数和数量
 *
 * 质数: 在大于1的自然数中 除了1 和 它本身 不会有其他 因数
 */
public class PrimeUtil {

    public static boolean isPrime(int number){
        int tmp = number;
        if(tmp < 2){
            return false;
        }
        //Math.sqrt 开方
        for (int i = 2; Math.sqrt(tmp) >= i;i++){
            if(tmp % i == 0){
                return false;
            }
        }
        System.out.println(number);
        return true;
    }

    public static void main2(String[] args) {
        /**
         * 串行执行
         */
        IntStream.range(1,1000000).filter(PrimeUtil::isPrime).count();

        /**
         * 将流并行化
         */
        IntStream.range(1,1000000).parallel().filter(PrimeUtil::isPrime).count();


        /**
         * 从集合得到并行流
         */
        List<Student> ss=new ArrayList<>();

        double ave=ss.stream().mapToInt(s-> s.score).average().getAsDouble();

        //并行流
        double pave=ss.parallelStream().mapToInt(s->s.score).average().getAsDouble();


        int[] array=new int[10000];
        Random ran=new Random();
        Arrays.setAll(array,(i)-> ran.nextInt());
        Arrays.parallelSetAll(array,(i)->ran.nextInt());
        /**
         * 串行排序
         */
        Arrays.sort(array);
        /**
         * 并行排序
         */
        Arrays.parallelSort(array);

        /**
         * 增强的Future : CompletableFuture
         * 实现了Future接口,更重要的是它也实现了CompletionStage接口
         * CompletionStage拥有40多个方法, 这个接口拥有如此多的方法, 是为了 函数式编程中的流式调用准备的.
         */
        CompletableFuture stage=new CompletableFuture();

        /**
         * 一连串调用 挨个执行
         */
        stage.thenApply(x-> square(x)).thenAccept(x -> System.out.println(x)).thenRun(() -> System.out.println());


    }

    public static Object square(Object x){
        return x;
    }

    public static class Student{
        int score;
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

    public static void main11(String[] args) throws InterruptedException {

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

    public static void main123(String[] args) throws ExecutionException, InterruptedException {
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
        System.out.println(future.get());

    }

    public static void main231(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 流式调用
         */
        CompletableFuture<Void> fu=CompletableFuture.supplyAsync(() -> calc(51))
                .thenApply((i)-> Integer.toString(i))
                .thenApply((str) -> "\""+str+"\"")
                .thenAccept(System.out::println);

        fu.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
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
}
