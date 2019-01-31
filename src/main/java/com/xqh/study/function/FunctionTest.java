package com.xqh.study.function;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionTest {

    public static void main(String[] args) {
//        DemoTest d=new DemoTest("nnn","code",new BigDecimal(1));
//
//        Optional<DemoTest> opt= Stream.of(d).findAny();
//        System.out.println(opt.isPresent() ? JSON.toJSONString(opt) : "'asdfasdff'");


        getList().stream().filter(el-> el.getPrice().compareTo(new BigDecimal("12")) > 0)
                .forEach(el->{
                    System.out.println(JSON.toJSONString(el));
                });
        /**
         * FunctionTest::getByStr
         * a-> getByStr(a)
         * a-> {
         *     return getByStr(a);
         * }
         */
        Arrays.asList("许墙绘","bbbb","ccccc").stream().map(FunctionTest::getByStr).collect(Collectors.toList()).forEach(d->{
            System.out.println(JSON.toJSONString(d));
        });

    }

    public <R> Stream<R> funTest(List<DemoTest> list, Function<DemoTest,R> cons){
        Optional<List<DemoTest>> any = Stream.of(list).filter(el-> el != null).map(el-> (List<DemoTest>)el).findAny();
        return any.isPresent() ? any.get().stream().map(cons) : Stream.empty();
    }

    private static List<DemoTest> getList(){
        return Arrays.asList(new DemoTest("1111","code1",new BigDecimal("11.11")),
                new DemoTest("2222","code2",new BigDecimal("22.22")),
                new DemoTest("3333","code3",new BigDecimal("33.33")));
    }

    public static String getByStr(String str){
        try {
            return URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DemoTest{
        private String name;
        private String code;
        private BigDecimal price;

        public DemoTest(String name){
            this.name=name;
        }

        public DemoTest(String name,String code,BigDecimal price){
            this.name=name;
            this.code=code;
            this.price=price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
