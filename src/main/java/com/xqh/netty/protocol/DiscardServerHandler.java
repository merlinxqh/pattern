package com.xqh.netty.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName DiscardServerHandler
 * @Description discard服务 (丢弃服务)
 *   他是一种丢弃了所有接受到的数据，并不做有任何的响应的协议。
 * 为了实现DISCARD协议，你唯一需要做的就是忽略所有收到的数据。让我们从处理器的实现开始，处理器是由Netty生成用来处理I/O事件的。
 * @Author xuqianghui
 * @Date 2019/1/11 15:48
 * @Version 1.0
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
