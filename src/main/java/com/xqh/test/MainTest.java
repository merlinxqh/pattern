package com.xqh.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * Created by leo on 2017/12/15.
 */
public class MainTest {
    public static void main(String[] args) throws Exception {
//        String key="obj.objItem.key";
//        System.out.println(key.substring(key.indexOf(".")+1));
        /**
         * 局部变量表 slot 复用 对 垃圾回收的影响
         *
         */
//        {
//            byte[] placeHolder=new byte[64*1024*1024];
//            //虽然  已经出了变量placeHolder的作用域,但是 其对应的slot还没有被其他变量复用
//            //所以作为GC Roots一部分的局部变量表仍然保持着对它的关联.
//        }
//        int a=0;
//        System.gc();
//        System.out.println(EncryUtils.getMd5("123456789"));

//        String url = "http://localhost:8800/pay?test=abc&name=hello&amount=122.0";
//        Long a = 5L;
//        Long b = 5L;
//        BigDecimal bd = NumberUtils.divide(new BigDecimal(a * b), new BigDecimal(100));
//
//        System.out.println(bd.longValue());
////        System.out.println(JSON.toJSONString(url.substring(url.indexOf("?")).split("&")));
////        switchTest(2);
////
//        String filePath = "D:\\testfile\\ffff\\sdfasdf\\";
//        filePathMkdirs(filePath);
        TestThread t = new TestThread();
        System.out.println(t.call());
    }


    public static class TestThread implements Callable<String>{
        @Override
        public String call() throws Exception {
            Thread.sleep(3000);
            return "success";
        }
    }

    public static void filePathMkdirs(String targetPath){
        File file = new File(targetPath.concat("mp3"));
        file.mkdirs();
        file = new File(targetPath.concat("pcm"));
        file.mkdirs();
    }

    public static int switchTest(int s){

        switch (s){
            case 1:
                System.out.println("1111111");
                break;
            case 2:
                System.out.println("22222222");
                break;
            case 3:
                System.out.println("333333333");
                break;
        }
        return -1;
    }
}
