package com.xqh.juc.NIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leo on 2017/10/12.
 * 用NIO构造多线程的Echo服务器
 */
public class NioMultiThreadEchoServer {

    /**
     * selector 用于管理所有的网络连接
     */
    private Selector selector;

    /**
     * 线程池用于对每个客户端进行相应的处理
     */
    private ExecutorService tp = Executors.newCachedThreadPool();

    /**
     * 统计服务器线程在一个客户端花费多少时间
     */
    private static Map<Socket,Long> time_stat = new HashMap<>(10240);

    /**
     * 启动NIO Server
     */
    private void startServer() throws IOException {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置为非阻塞模式

//        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(),8000);
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);
        /**
         * acceptKey 是Channel 和 Selector 两者服务关系的 契约
         */
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        for(;;){
            /**
             * select()方法是一个阻塞 方法
             * 如果当前没有任何准备好的, 它就会等待, 一旦有数据可读, 他就会返回
             * 返回值 是已经准备好的SelectionKey的数量
             */
            selector.select();
            /**
             * 获取准备好的SelectionKey
             */
            Set readKeys=selector.selectedKeys();
            Iterator i = readKeys.iterator();
            long e=0;
            while (i.hasNext()){
                SelectionKey sk = (SelectionKey) i.next();
                /**
                 * remove()方法非常重要,处理完一个SelectionKey必须将其删除 否者就会处理相同的SelectionKey
                 */
                i.remove();
                /**
                 * 在Acceptable状态 , 进行客户端接收
                 */
                if(sk.isAcceptable()){
                    doAccept(sk);
                /**
                 * 判断Channel是否已经可读了
                 * 为了处理系统处理每一个连接的时间
                 */
                }else if(sk.isValid() && sk.isReadable()){
                   if(!time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
                       time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
                   }
                   doRead(sk);
                    /**
                     * 判断通道Channel是否准备好进行写.
                     * 同时写入完成后,输出处理这个Socket连接的时间
                     */
                }else if(sk.isValid() && sk.isWritable()){
                    doWrite(sk);
                    e=System.currentTimeMillis();
                    long b=time_stat.remove(((SocketChannel)sk.channel()).socket());
                    System.out.println("spend:"+(e-b)+" ms");
                }
            }
        }

    }


    private void doAccept(SelectionKey sk){
       ServerSocketChannel server = (ServerSocketChannel) sk.channel();
       SocketChannel clientChannel;
       try {
           clientChannel = server.accept();
           clientChannel.configureBlocking(false);

           /**
            * 这一步很关键, 将新生成的channel注册到selector选择器上,
            * 告诉selector, 我现在对 读(OP_READ) 操作感兴趣.
            * 这样Selector发现这个channel已经准备好读时, 就会给线程一个通知.
            */
           SelectionKey clientKey=clientChannel.register(selector, SelectionKey.OP_READ);
           EchoClient echoClient=new EchoClient();
           clientKey.attach(echoClient);

           InetAddress clientAddress=clientChannel.socket().getInetAddress();
           System.out.println("Accept connection from "+clientAddress.getHostAddress()+".");

       } catch (Exception e) {
           System.out.println("Failed to accept new client...");
           e.printStackTrace();
       }
    }


    /**
     * 当Channel可以读取时, doRead()就会被调用.
     * @param sk
     */
    private void doRead(SelectionKey sk){
        SocketChannel channel=(SocketChannel) sk.channel();
        ByteBuffer bb=ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(bb);
            if(len < 0){
                disconnect(sk);//TODO
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        //读取完成后,重置缓冲区, 为处理数据做准备
        bb.flip();
        tp.execute(new HandleMsg(sk,bb));
    }


    /**
     * 写入操作
     * @param sk
     */
    private void doWrite(SelectionKey sk){
       SocketChannel channel= (SocketChannel) sk.channel();
       EchoClient echoClient= (EchoClient) sk.attachment();
       LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

       ByteBuffer bb=outq.getLast();
       try {
           int len = channel.write(bb);
           if(len == -1){
               disconnect(sk);
               return;
           }

           if(bb.remaining() == 0){
               //写完,移除
               outq.removeLast();
           }
       } catch (IOException e) {
           System.out.println("Failed to write to client.");
           e.printStackTrace();
           disconnect(sk);
       }
        /**
         * doWrite()中最重要 也是最容易忽略的事
         * 是在全部数据发送完成后(outq.size() == 0),
         * 需要将 写(OP_WRITE)从感兴趣的事件中踢出.
         * 如果不这么做,每次Channel准备好写时都会执行doWrite()方法.
         */
       if(outq.size() == 0){
           sk.interestOps(SelectionKey.OP_READ);
       }

    }

    /**
     * 断开连接
     * 自己写的
     * @param sk
     */
    private void disconnect(SelectionKey sk){
        SocketChannel channel= (SocketChannel) sk.channel();
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class EchoClient{
        private LinkedList<ByteBuffer> outq;

        EchoClient(){
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutputQueue(){
            return outq;
        }

        public void enqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable{

        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk,ByteBuffer bb){
            this.sk=sk;
            this.bb=bb;
        }

        @Override
        public void run() {
            EchoClient echoClient= (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            //TODO 处理具体的业务逻辑

            /**
             * 数据处理完成后,就可以准备将结果写回到客户端,
             * 因此 重新注册感兴趣的的消息事件, 将写操作(OP_WRITE) 也作为感兴趣的事件进行提交.
             * 这样通道准备好写入的时候就能通知线程.
             */
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强迫selector立即返回
            selector.wakeup();
        }
    }

    public static void main(String[] args) throws IOException {
        NioMultiThreadEchoServer server=new NioMultiThreadEchoServer();
        server.startServer();
    }
}




