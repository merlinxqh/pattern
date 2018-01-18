package com.xqh.algorithm.exercises.n01;

/**
 * 编写递归方法, 使输入 abc , 能输出 abc,acb,bac,bca,cab,cba
e
 */
public class Ex06 {

    public static void main(String[] args) {
        String s = "1234";// 这里是要用到的所有数组成的一个字符串,其它字符同样适用
        char[] c = s.toCharArray();
        zuhe(c,c.length,0);
        System.out.println("可能的组合数：" + kk);
    }
    static int kk = 0;

    /**
     * 1,2,3,4
     * 1,2,4,3
     * 1,3,2,4
     * 1,3,4,2
     * 1,4,3,2
     * 1,4,2,3
     *
     * 2,1,3,4
     * 2,1,4,3
     * 2,3,1,4
     * 2,3,4,1
     * 2,4,3,1
     * 2,4,1,3
     *
     * 3,2,1,4
     * 3,2,4,1
     * 3,1,2,4
     * 3,1,4,2
     * 3,4,1,2
     * 3,4,2,1
     *
     * 4,2,3,1
     * 4,2,1,3
     * 4,3,2,1
     * 4,3,1,2
     * 4,1,3,2
     * 4,1,2,3
     * @param array
     * @param n
     * @param k
     */
    private static void zuhe(char[] array,int n,int k) {
        if (n == k) {
            String str = new String(array);
            System.out.println(str);
            ++kk;
        } else {
            for (int i = k; i < n; i++) {
                swap(array,k,i);
                zuhe(array,n,k + 1);
                swap(array,i,k);
            }
        }
    }
    private static void swap(char[] a,int x,int y) {
        char temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    /**
     * 嵌套两层 循环 当外层循环 序号 不等于 里层循环序号 时 对换 位置 此时 是一个有效的组合
     *
     * @param str
     */
    public static void permute(String str){
       char[] chars = str.toCharArray();
       StringBuffer sb=new StringBuffer();
       //以下代码有问题
       for(int i=0;i<chars.length;i++){
          for(int j=0;j<chars.length;j++){
              if(i != j){
                 char[] tmpArr=str.toCharArray();
                 char tmp = tmpArr[j];
                 tmpArr[j] = tmpArr[i];
                 tmpArr[i] = tmp;
                 System.out.println(String.valueOf(tmpArr));
              }
          }
       }
    }

    private static void permute(char[] chars, int low, int high){

    }
}
