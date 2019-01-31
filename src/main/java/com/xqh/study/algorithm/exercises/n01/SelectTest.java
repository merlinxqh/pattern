package com.xqh.study.algorithm.exercises.n01;

/**
 * 程序解决选择问题,从N个数中,按升序(或者 降序)排序, 取出第K个数
 */
public class SelectTest {

    private static final int N = 100000;

    /**
     * 同样长度 同样 数据的 数组
     * 插入排序耗时:914
       冒泡排序耗时:15972
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = new int[N];
        int[] arr2 = new int[N];
        for(int i = 0;i<N;i++){
            int tmp = (int)(Math.random()*1000000);
            arr[i] = tmp;
            arr2[i] = tmp;
        }

        /**
         * 插入排序
         */
        long start=System.currentTimeMillis();
        for(int i=0;i<N-1;i++){
              int j = i+1;
              int tmp = arr[j];
              while(j > 0 && arr[j - 1] > tmp){
                  arr[j]=arr[j-1];
                  j--;
              }
              if(j != i+1){
                  arr[j] = tmp;
              }
        }
        System.out.println("插入排序耗时:"+(System.currentTimeMillis()-start));

        long start2=System.currentTimeMillis();
        /**
         * 冒泡排序法
         */
        for(int i=0;i<N;i++){
            for(int j=i+1;j<N;j++){
                if(arr2[i] > arr2[j]){
                    int tmp = arr2[j];
                    arr2[j]=arr2[i];
                    arr2[i]=tmp;
                }
            }
        }
        System.out.println("冒泡排序耗时:"+(System.currentTimeMillis()-start2));

    }
}
