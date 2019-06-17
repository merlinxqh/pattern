package com.xqh.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xqh.utils.ExcelReader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

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

//          File file = new File("D:\\chromeDownload\\nlu指令对比结果 (22).xlsx");
//        System.out.println(file.getName());
//        System.out.println(file.getName().contains("."));

//        String test = "/fasdf";
//        System.out.println(test.substring(1));

//        String url = "http://scv2.sh.hivoice.cn:80/service/iss?appendLength=1&wakeupword=%E5%B0%8F%E8%8C%82%E5%B0%8F%E8%8C%82&city=%E6%B7%B1%E5%9C%B3%E5%B8%82&appver=1.0.1&filterName=nlu3&screen=&platform=&audioUrl=false&viewid=&scenario=&udid=LTY2OTg5Mjk4NTAwNWE3YmU1ZTE3YmUxqq&dpi=&filterUrl=http%3A%2F%2F47.107.47.68%3A19998%2Fprocess%2Ftr%2FdataProcess&ver=3.2&method=iss.getTalk&gps=22.554349%2C113.948661&history=&oneshotKeyProperty=wakeup&additionalService=athenaAppService&voiceid=2fa3cfabce6ff4640889f5236ce5028f&appsig=7FF47E6F31169EDFCC4CA58E01613DC3A164E42F&fullDuplex=false&time=2019-03-2110%3A25%3A20&req_nlu_length=1&returnType=json";
//        String params = url.substring(0, url.indexOf("?"));
////        for(String p:params.split("&")){
////            System.out.println(p);
////        }
//        System.out.println(params.substring(0, params.length()-1));

//        File file = new File("E:\\document\\yzs\\program\\班课学生上传模板.xlsx");
//        List<String[]> list = ExcelReader.getExcelData(file, 1);
//        for(String[] a:list){
//            System.out.println(a[1]);
//        }
//        String str = "阔四你好\n" +
//                "科视你好\n" +
//                "你好科视\n" +
//                "你好阔四\n";
//        System.out.println(str.contains("\n"));
//        System.out.println(JSON.toJSONString(str.split("\n")));
//        String str = "D:\\TestSpace\\out.pcm";
//        System.out.println(str.substring(str.lastIndexOf("\\")+1).replaceAll(".pcm", ""));

//        String t = "最终幻想7";
//        System.out.println(t.substring(0, t.lastIndexOf("7")));
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExcelModel{

        private String udid;

        private String sessionId;
    }




    public static void callableThreadTest(){
        TestThread t = new TestThread();
        TestThread t2 = new TestThread();
        FutureTask<String> f1 = new FutureTask<>(t);
        FutureTask<String> f2 = new FutureTask<>(t2);
        new Thread(f1).start();
        new Thread(f2).start();
    }


    public static class TestThread implements Callable<String>{
        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()+" in....");
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
