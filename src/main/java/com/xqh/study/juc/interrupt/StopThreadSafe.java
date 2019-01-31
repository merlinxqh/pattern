package com.xqh.study.juc.interrupt;

/**
 * Created by leo on 2017/9/14.
 */
public class StopThreadSafe {

    public static User u=new User();

    public static class User{
        private int id;
        private String name;

        public User(){
            this.id=0;
            this.name="0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=]"+id+", name="+name+"]";
        }
    }

    public static class ChangeObjectThread extends Thread{

        volatile boolean stopme=false;

        public void stopMe(){
            stopme=true;
        }

        @Override
        public void run() {
            while (true){
                if(stopme){
                    System.out.println("exit by stopme");
                    break;
                }
                synchronized (u){
                    int v= (int) (System.currentTimeMillis()/1000);
                    u.setId(v);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    if(u.getId() != Integer.valueOf(u.getName())){
                        System.out.println(u.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
       new ReadObjectThread().start();

       while (true){
           ChangeObjectThread t=new ChangeObjectThread();
           t.start();
           Thread.sleep(150);
           /**
            * 中断线程, 是实例方法
            * 它通知目标线程中断,也就是设置中断标志位
            * 中断标志位标识当前线程已经中断了.
            */
           t.interrupt();//
           /**
            * 判断是否被中断,实例方法
            * 判断当前线程是否有中断(根据中断标志位)
            */
           t.isInterrupted();
           /**
            * 判断是否被中断,静态方法
            * 也是用来判断当前线程的中断状态, 但同时会清除当前线程的中断标志位状态
            */
           Thread.interrupted();
           t.stopMe();//自己实现 中断方法
       }
    }
}
