package com.xqh.study.juc.design.insertSort;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/10/11.
 */
public class InsertSort {

    static int[] arry={11,3,120,9,34,35,99,1,5,22,55,9,7,14,45,66,77,44,15,24,36,28,97};
    static ExecutorService pool= Executors.newCachedThreadPool();

    /**
     * 插入排序
     * (数组 分为两部分 1. 已经排好序的  2. 没有排好序的)
     *  循环将 没排好序的 插入 已经排好序的 部分
     * @param arr
     */
    public static void insertSort(int[] arr){
        int length=arr.length;

        int j,i,key;

        for(i = 1;i<length;i++){
            //key为准备要插入的元素
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key){
                arr[j+1]=arr[j];
                j--;
            }
            //找到合适的位置插入key
            arr[j+1]=key;
        }
    }

    /**
     * 希尔排序
     * 将整个数组根据间隔h分割为若干个子数组.
     * @param arr
     */
    public static void shellSort(int[] arr){
        //计算出最大的h值 ()
       int h=1;
       while (h <= arr.length/3){
           h=h*3+1;
       }
       //当h=1时,退化为一次 插入排序
       while (h>0){
           for(int i=h;i<arr.length;i++){
               if(arr[i] < arr[i-h]){
                   int tmp=arr[i];
                   int j=i-h;
                   while (j >= 0 && arr[j] > tmp){
                       arr[j+h] = arr[j];
                       j -= h;
                   }
                   arr[j+h]=tmp;
               }
           }
           //计算出下一个h
           h = (h-1)/3;
       }
    }

    /**
     * 并行希尔排序
     */
    public static void pShellSort() throws InterruptedException {
        //计算出最大h
        int h=1;
        CountDownLatch latch=null;
        while(h <= arry.length/3){
            h = h*3+1;
        }

        while (h > 0) {
            System.out.println("h = "+h);
            if(h >= 4){
                latch=new CountDownLatch(arry.length -h);
            }
            for(int i=h;i<arry.length;i++){
                if(h >= 4){
                    pool.execute(new ShellSortTask(i,h,latch));
                }else{
                    if(arry[i] < arry[i-h]){
                        int tmp=arry[i];
                        int j=i-h;
                        while (j >= 0 && arry[j] > tmp){
                            arry[j+h]=arry[j];
                            j -= h;
                        }
                        arry[j+h]=tmp;
                    }
                }
            }
            //等待线程执行完,进入下一次排序
            if(null != latch){
                latch.await();
            }
            //计算出下一个h值
            h = (h-1)/3;
        }
    }



    public static class ShellSortTask implements Runnable{
        int i=0;
        int h=0;
        CountDownLatch latch;

        public ShellSortTask(int i,int h,CountDownLatch latch){
            this.i=i;
            this.h=h;
            this.latch=latch;
        }

        @Override
        public void run() {
            if(arry[i] < arry[i-h]){
                int tmp=arry[i];
                int j=i-h;
                while (j>=0 && arry[j]>tmp){
                    arry[j+h]=arry[j];
                    j -= h;
                }
                arry[j+h]=tmp;
            }
            latch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        insertSort(arry);
//        shellSort(arry);
        pShellSort();
        System.out.println(JSON.toJSONString(arry));
    }
}
