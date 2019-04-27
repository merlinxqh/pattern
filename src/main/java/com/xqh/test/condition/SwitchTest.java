package com.xqh.test.condition;

/**
 * @ClassName SwitchTest
 * @Description switch 测试
 * @Author xuqianghui
 * @Date 2019/3/29 10:26
 * @Version 1.0
 */
public class SwitchTest {

    public static void main(String[] args) {
        switchTest(2);
    }

    /**
     * 这里是注释
     * @param value
     */
    public static void switchTest(int value){
        switch (value){
            case 1:
                System.out.println("value ===>"+1);
                break;
            case 2:
                System.out.println("value ===>"+2);
                break;
            default:
                System.out.println("value is default...");
                break;
        }

    }
}
