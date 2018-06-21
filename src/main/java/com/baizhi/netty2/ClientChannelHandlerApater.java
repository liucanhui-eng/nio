package com.baizhi.netty2;

import com.baizhi.entity.User;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class ClientChannelHandlerApater extends ChannelHandlerAdapter {
    @Override
    //捕获异常
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出错了:" +cause.getMessage());
    }
    @Override
    //向服务器发送请求
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //获取数据的容器--ByteBuf
//      ByteBuf buf = Unpooled.buffer();
        ByteBuf buf = ctx.alloc().buffer();
        //写入数据
        buf.writeBytes("闺中少妇不知愁".getBytes());

//      发送请求
        //ctx.writeAndFlush(buf);
        ChannelFuture channelFuture = ctx.writeAndFlush(new User());
        //监听序列化异常
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        //发生异常时关闭连接
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接收数据
        System.out.println(msg);
    }
}
