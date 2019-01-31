package com.xqh.study.jvm;

import java.io.*;

/**
 * Created by leo on 2018/1/23.
 *
 */
public class StringIntern {
    /**
     * 在jdk1.6
     *    以下代码打印出 两个false
     *    因为在jdk1.6中,intern()方法会把首次遇到的字符串实例 复制到 永久代中, 返回的也是永久代中这个字符串实例的引用.
     *    而StringBuilder创建的字符串实例在java堆上, 所以必然不是同一个引用.
     *
     * 在jdk1.7
     *    以下代码打印出 一个true 一个 false
     *    因为在jdk1.7中,intern()方法不会再复制实例, 只是在常量池中记录首次出现的实例引用.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        String str3 = "计算机软件";
//
//        String[] a = new String[10];
//        System.out.println(a.length == 10 && a[1] == a[2] && a[1] == null);
////        System.out.println(str1 == str3);
////        System.out.println(str1.intern() == str1);
////
////        System.out.println(str1.intern() == str3.intern());
////
////        String str2 = new StringBuilder("ja").append("va").toString();
////
////        System.out.println(str2.intern() == str2);
//
//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                pong();
//            }
//        };
//        t.run();
//        System.out.println("ping");
//
//        Map<String,Object> map = new HashMap<>();
//
//        List<String> list = new ArrayList<>();

//        ((StringIntern)null).pong();
//        new BufferedWriter(new FileWriter("a.txt"));

//        try {
//            throw new IOException("ddd");
//        }catch (IOException e){
//            System.out.println("catch ioException");
//        }catch (Exception e){
//            System.out.println("catch exception");
//        }

    }

    public static void pong(){
        System.out.println("pong");
    }
}
