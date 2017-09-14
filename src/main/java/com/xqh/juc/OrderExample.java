package com.xqh.juc;

/**
 * Created by leo on 2017/9/13.
 */
public class OrderExample {

    int a=0;
    boolean flag=false;
    public void write(){
        a=1;
        flag=true;
    }

    public void read(){
       if(flag){
           int i=a+1;
           System.out.println(i);
       }
    }

    static class WriteThread implements Runnable{
        OrderExample example;

        public WriteThread(OrderExample example){
            this.example=example;
        }

        @Override
        public void run() {
            example.write();
        }
    }

    static class ReadThread implements Runnable{
        OrderExample example;

        public ReadThread(OrderExample example){
            this.example=example;
        }
        @Override
        public void run() {
           example.read();
        }
    }


    public static void main(String[] args) {
        OrderExample example=new OrderExample();

        new Thread(new WriteThread(example)).start();
        new Thread(new ReadThread(example)).start();
    }

}
