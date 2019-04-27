package com.xqh.test.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName ListTest
 * @Description {@link java.util.ArrayList}
 * @Author xuqianghui
 * @Date 2019/3/27 17:48
 * @Version 1.0
 */
public class ListTest {

    public static void main(String[] args) {
//        wrongRemoveTest();
        String[] arry = "asdf,asdfgg,".split(",");
        Stream.of(arry).forEach(f->{
            System.out.println("++++>"+f);
        });

    }

    /**
     * 错误的集合remove方法
     */
    public static void wrongRemoveTest() {
        List<String> list = new ArrayList<>(2);
        list.add("1");
        list.add("2");
        for (String str : list) {
            if ("1".equals(str)) {//换成 "2" 结果不一样
                list.remove(str);
            }
        }
    }

    /**
     * 正确的集合remove
     */
    public static void removeTest(){
        List<String> list = new ArrayList<>(2);
        list.add("1");
        list.add("2");
        Iterator<String> iterator =list.iterator();
        while (iterator.hasNext()){
            String item = iterator.next();
            if("1".equals(item)){
                iterator.remove();
            }
        }
    }
}
