package com.xqh.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xqh.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName StringSyncTest
 * @Description string 同步锁 测试
 * @Author xuqianghui
 * @Date 2019/7/23 9:51
 * @Version 1.0
 */
@Slf4j
public class StringSyncTest {

    private static List<String> storeList = Lists.newArrayList();

    public static void syncTest(String name){
        synchronized (name){
            if(storeList.contains(name)){
                log.info("name ==>{} has exists.", name);
                return;
            }
            storeList.add(name);
        }
    }

    private static List<String> nameList = Arrays.asList("lily", "pis", "burning", "430", "cty", "jjz", "maybe");


    public static String getRandomName(){
        Random random = new Random();

        int idx = random.nextInt(nameList.size());
        return nameList.get(idx);
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i < 10000; i++){
            ThreadPoolUtils.execute(()->{
                syncTest(getRandomName());
            });
        }
        Thread.sleep(10000);
        log.info(JSON.toJSONString(storeList));
    }
}
