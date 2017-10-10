package com.xqh.juc.design.future;

/**
 * Created by leo on 2017/10/10.
 */
public class Main {

    public static void main(String[] args) {
        Client client=new Client();
        Data data=client.request("name");
        System.out.println("client请求完毕");
        try {
            //模拟对其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("realData= "+data.getResult());
    }
}
