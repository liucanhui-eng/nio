package com.baizhi.nio;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocket3456 {
    public static void main(String[] args) throws Exception {
        m2();
    }

    public static void m1() throws Exception {
        //获取服务端socket
        ServerSocketChannel channel = ServerSocketChannel.open();
        //监听端口
        channel.bind(new InetSocketAddress("localhost", 8989));
        //等待响应
        SocketChannel socketChannel = channel.accept();
        //处理请求
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        while (true) {
            byteBuffer.clear();
            int read = socketChannel.read(byteBuffer);
            if (read == -1) break;
            byteArrayOutputStream.write(byteBuffer.array(), 0, read);
        }
        System.out.println("接收到的数据：" + new String(byteArrayOutputStream.toByteArray()));
        //响应
        String res = "春日凝装上翠楼";
        socketChannel.write(ByteBuffer.wrap(res.getBytes()));
        //通知客户端响应结束
        socketChannel.shutdownOutput();
        //关闭资源
        socketChannel.close();
    }

    public static void m2() throws Exception {
        //获取服务端socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8989));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建选择器
        Selector selector = Selector.open();
        //注册
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环
        while (true) {
            System.out.println("处理请求");
            System.out.println(selector.select());
            System.out.println("====================");
            if (selector.select() > 0) {
                //获取时间处理队列中的所有事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        System.out.println("处理accept");
                        //获取转发请求的channel
                        ServerSocketChannel selectionKey1 = (ServerSocketChannel) selectionKey.channel();
                        //注册读操作
                        selectionKey1.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        System.out.println("读操作");
                        //获取读写请求的channel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int i = channel.read(byteBuffer);
                        if (i == -1) {
                            System.out.println("读完了");
                            break;
                        }
                        baos.write(byteBuffer.array(), 0, i);
                        System.out.println("接收到的数据" + new String(baos.toByteArray()));

                    }
                    iterator.remove();
                }
            }
        }
    }

}
