package com.xqh.algorithm.exercises.n01;

/**
 * 编写递归方法, 使输入 abc , 能输出 abc,acb,bac,bca,cab,cba
e
 */
public class Ex06 {

    public static void main(String[] args) {
      permute("abc");
    }

    /**
     * 嵌套两层 循环 当外层循环 序号 不等于 里层循环序号 时 对换 位置 此时 是一个有效的组合
     * @param str
     */
    public static void permute(String str){
       char[] chars = str.toCharArray();
       StringBuffer sb=new StringBuffer();
       //以下代码有问题
//       for(int i=0;i<chars.length;i++){
//          for(int j=0;j<chars.length;j++){
//              if(i != j){
//                  char[] tmpArr=str.toCharArray();
//
//                 char tmp = tmpArr[j];
//                  tmpArr[j] = tmpArr[i];
//                  tmpArr[i] = tmp;
//                  System.out.println(String.valueOf(tmpArr));
//              }
//          }
//       }
    }

    private static void permute(char[] chars, int low, int high){

    }
}
