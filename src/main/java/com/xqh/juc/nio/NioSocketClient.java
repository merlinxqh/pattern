package com.xqh.juc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Created by leo on 2017/10/12.
 * NIO构建客户端
 */
public class NioSocketClient {

    private Selector selector;

    /**
     * 初始化
     * @param ip
     * @param port
     * @throws IOException
     */
    public void init(String ip,int port) throws IOException {
        SocketChannel channel=SocketChannel.open();
        channel.configureBlocking(false);

        this.selector = SelectorProvider.provider().openSelector();
        /**
         * 将SocketChannel绑定到Socket上
         * 但由于当前Channel是非阻塞的,因此connect()方法返回时,连接并不一定建立成功.
         * 在后续时候这个连接时 还需要使用 finishConnect()方法确认.
         */
        channel.connect(new InetSocketAddress(ip,port));
        /**
         * 将Channel和Selector绑定,并且注册了感兴趣的事件作为连接
         * (OP_CONNECT)
         */
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    /**
     * 执行逻辑
     */
    public void working() throws IOException {
       while (true){
           if(!selector.isOpen()){
                break;
           }
           selector.select();
           Iterator<SelectionKey> ite=selector.selectedKeys().iterator();
           while (ite.hasNext()){
               SelectionKey sk=ite.next();
               ite.remove();
               //连接事件发生
               if(sk.isConnectable()){
                   connect(sk);
               }else if(sk.isReadable()){
                   read(sk);
               }
           }
       }
    }

    public void connect(SelectionKey sk){

    }

    public void read(SelectionKey sk){

    }
}
