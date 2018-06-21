package com.baizhi.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class ServerChannelHandlerApater extends ChannelHandlerAdapter {
    @Override
    //捕获错误方法
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出错了 "+cause.getMessage());
    }


    //接收请求的方法 //接收byteBuf
    public void channelRead123(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*  msg为客户端请求的数据
            netty默认传输的数据均为byteBuf或FileRegion
        */
        ByteBuf buf= (ByteBuf) msg;
        //将请求的数据转化为字符串
        String s = buf.toString(CharsetUtil.UTF_8);
        System.out.println("请求数据："+s);
        //获取ByteBuf
        //ByteBuf res= Unpooled.buffer();
        ByteBuf res = ctx.alloc().buffer();
        //向byteBuf中写数据
        res.writeBytes("春日凝装上翠楼".getBytes());
        //向服务端发相应
        ChannelFuture channelFuture = ctx.writeAndFlush(res);
        //监听序列化异常
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        //发生异常时关闭连接
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        //关闭通道
        //channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
    //接收请求的方法 //接收Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Object o = msg;
        System.out.println("请求数据："+o);
        //向服务端发相应
        ChannelFuture channelFuture = ctx.writeAndFlush("ghgh");
        //监听序列化异常
        channelFuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        //发生异常时关闭连接
        channelFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        //关闭通道
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

}
