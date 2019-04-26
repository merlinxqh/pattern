package com.xqh.test.date;

import com.xqh.test.LambdaTest.TestDemo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * @ClassName InstantTest
 * @Description {@link java.time.Instant}
 * @Author xuqianghui
 * @Date 2019/3/29 13:51
 * @Version 1.0
 */
@Slf4j
public class InstantTest {

    public static void main(String[] args) {
        Instant instant = Instant.now();
        System.out.println("instant ==>"+instant.toEpochMilli());
        Date now = new Date();
        System.out.println("date ==>"+now.getTime());

        try(FileInputStream inputStream = new FileInputStream(new File("test"))){
            System.out.println(inputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static Optional<TestDemo> getData(){
        return Optional.ofNullable(TestDemo.builder().build());
    }
}
