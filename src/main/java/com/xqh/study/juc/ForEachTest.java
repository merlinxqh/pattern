package com.xqh.study.juc;

/**
 * Created by leo on 2017/9/28.
 */
public class ForEachTest {

    public static void main(String[] args) {
        position1: for(int i=0;i<10;i++){
            System.out.println("this is i........"+i);
            position2: for(int j=0;j<20;j++){
                System.out.println("this is j........"+j);
                if(j>=5){
                    continue position1;
                    //可以 continue , break 任意一个循环
                }
            }
        }
    }
}
