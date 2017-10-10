package com.xqh.juc.design.pSort;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/10/10.
 */
public class PSort {

    static int[] arr={11,2,56,78,14,25,69,3,18,157,1,64,15,46};

    /**
     * 奇偶交换排序 串行实现
     */
    public static void oddEvenSort(){
        int sExchFlag=1,start=0;
        while(sExchFlag == 1 || start == 1){
            sExchFlag = 0;
            for(int i=start;i<arr.length-1;i+=2){
                if(arr[i] > arr[i+1]){
                   int tmp=arr[i];
                   arr[i]=arr[i+1];
                   arr[i+1]=tmp;
                   sExchFlag = 1;
                }
            }
            if(start == 0){
                start = 1;
            }else
                start=0;
        }
    }


    /**
     * 并行实现
     * @param args
     */
    static int exchFlag=1;

    static ExecutorService pool= Executors.newCachedThreadPool();

    static synchronized void setExchFlag(int v){
        exchFlag=v;
    }

    static synchronized int getExchFlag(){
        return exchFlag;
    }

    public static class OddEvenSortTask implements Runnable{
        int i;
        CountDownLatch latch;

        public OddEvenSortTask(int i,CountDownLatch latch){
            this.i=i;
            this.latch=latch;
        }
        @Override
        public void run() {
            if(arr[i]>arr[i+1]){
                int tmp=arr[i];
                arr[i]=arr[i+1];
                arr[i+1]=tmp;
                setExchFlag(1);
            }
            latch.countDown();
        }
    }

    public static void pOddEvenSort() throws InterruptedException {
        int start=0;
        while (getExchFlag() == 1 || start == 1){
            setExchFlag(0);
            //偶数的数组长度,当start为1时,只有len/2 - 1个线程
            CountDownLatch latch=new CountDownLatch(arr.length/2 - (arr.length % 2 == 0? start:0));
            for(int i=start;i<arr.length-1;i+=2){
               pool.submit(new OddEvenSortTask(i,latch));
            }
            //等待所有线程结束
            latch.await();
            if(start == 0){
                start = 1;
            }else
                start = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        pOddEvenSort();
        System.out.println(JSON.toJSONString(arr));
    }
}
