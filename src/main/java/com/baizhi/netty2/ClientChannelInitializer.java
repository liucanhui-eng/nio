package com.baizhi.netty2;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ObjectMessageToMessageDecoder());
        pipeline.addLast(new ObjectMessageToMessageEncoder());
        pipeline.addLast(new ClientChannelHandlerApater());

    }

}
