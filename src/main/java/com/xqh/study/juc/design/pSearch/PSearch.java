package com.xqh.study.juc.design.pSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leo on 2017/10/10.
 * 并行搜索
 */
public class PSearch {
    static int[] arr;

    static ExecutorService pool= Executors.newCachedThreadPool();

    static final int THREAD_NUM=2;

    static AtomicInteger result=new AtomicInteger(-1);


    public static int search(int searchValue, int beginPos, int endPos){
        int i=0;
        for(i = beginPos;i<endPos;i++){
            if(result.get() >= 0){
                return result.get();
            }
            if(arr[i] == searchValue){
                //如果设置失败  说明其他线程已经找到了
                if(!result.compareAndSet(-1,i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
        int arrSubSize = arr.length/THREAD_NUM +1;
        List<Future<Integer>> re = new ArrayList<>();
        for(int i=0;i<arr.length;i+=arrSubSize){
            int end=i+arrSubSize;
            if(end > arr.length){
                end=arr.length;
            }
            re.add(pool.submit(new SearchTask(searchValue,i,end)));
        }
        for(Future<Integer> fu:re){
            if(fu.get() >= 0){
                return fu.get();
            }
        }
        return -1;
    }

    public static class SearchTask implements Callable<Integer>{

        int beginPos,endPos,searchValue;

        public SearchTask(int searchValue,int beginPos,int endPos){
            this.searchValue=searchValue;
            this.beginPos=beginPos;
            this.endPos=endPos;
        }

        @Override
        public Integer call() throws Exception {
            int ret=search(searchValue,beginPos,endPos);
            return ret;
        }
    }

}
