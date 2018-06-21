package com.baizhi.nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws Exception {
        //获取 channel 缓存区
        SocketChannel channel = SocketChannel.open();
        //绑定ip和端口
        channel.connect(new InetSocketAddress("localhost", 8989));
        //发送请求
        channel.write(ByteBuffer.wrap("闺中少妇不知愁".getBytes()));
        channel.shutdownOutput();
        //接收响应
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            buffer.clear();
            int read = channel.read(buffer);
            if(read==-1)break;
            //buffer.flip();
            byteArrayOutputStream.write(buffer.array(),0,read);
        }
        System.out.println(new String(byteArrayOutputStream.toByteArray()));
        //关闭资源
        channel.close();
    }
}
