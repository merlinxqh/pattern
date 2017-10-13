package com.xqh.juc.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by leo on 2017/10/13.
 * AIO Echo server
 */
public class AIOEchoServer {

    public static final int PORT=8000;

    private AsynchronousServerSocketChannel server;

    public AIOEchoServer() throws IOException {
        this.server=AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(PORT));
    }

    public void start(){
        System.out.println("Server listen on "+PORT);
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer=ByteBuffer.allocate(1024);

            @Override
            /**
             * 异步操作成功 调用
             */
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                /**
                 * 当completed()方法被执行时,意味着已经有客户端成功连接了.
                 */
                System.out.println("current thread ==>"+Thread.currentThread().getName());
                Future<Integer> writeResult=null;
                try {
                    buffer.clear();
                    /**
                     * 读取客户端数据
                     * read()方法是异步的,返回结果是一个Future
                     * 这里 为了编程方便 用Future.get()方法将异步直接转成同步
                     * get()方法执行完之后,说明数据已经读取完
                     */
                    result.read(buffer).get(100, TimeUnit.SECONDS);
                    buffer.flip();
                    /**
                     * 将数据回写到客户端
                     * AsynchronousSocketChannel.write()方法也是个异步方法,返回Future对象
                     */
                    writeResult=result.write(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        /**
                         * 服务器进行下一个客户端连接准备
                         * 同时关闭当前正在处理的客户端连接.
                         * 但是在关闭之前得先确定之前的write()操作已完成 因此用Future.get()方法进行等待
                         */
                        server.accept(null,this);
                        writeResult.get();
                        result.close();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }

            @Override
            /**
             * 异步操作失败 调用
             */
            public void failed(Throwable exc, Object attachment) {
                System.out.println("filed:"+exc);
            }
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new AIOEchoServer().start();
        /**
         * 因为start()方法使用的是异步方法,因此它会立即返回
         * 因此以下等待语句是必须的,否则start()方法结束后,不等待客户端的到来
         * 程序已经完成,主线程将退出
         */
        while (true){
            Thread.sleep(1000);
        }
    }

}
