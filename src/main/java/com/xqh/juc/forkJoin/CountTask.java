package com.xqh.juc.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by leo on 2017/9/23.
 * 分而治之
 * 数列求和
 * RecursiveTask 带返回值的任务
 */
public class CountTask extends RecursiveTask<Long> {

    private static final int THRESHOLD=10000;
    private long start;
    private long end;

    public CountTask(long start,long end){
        this.start=start;
        this.end=end;
    }


    @Override
    protected Long compute() {
        long sum=0;
        boolean canCompute=(end-start)<THRESHOLD;
        if(canCompute){
            for(long i=start;i<=end;i++){
                sum+=i;
            }
        }else{
            //分成100个小任务
            long step=(start+end)/100;
            List<CountTask> subTasks=new ArrayList<>();
            long pos=start;
            for(int i=0;i<100;i++){
                long lastOne=pos+step;
                if(lastOne>end){
                    lastOne=end;
                }
                CountTask subTask=new CountTask(pos,lastOne);
                System.out.println("pos="+pos+" lastOne="+lastOne);
                pos+=step+1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for(CountTask task:subTasks){
                sum+=task.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        CountTask task=new CountTask(0,200000L);
        ForkJoinTask<Long> result=forkJoinPool.submit(task);
        try {
            long res=result.get();
            System.out.println("sum="+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
