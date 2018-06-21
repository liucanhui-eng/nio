package com.baizhi.bio;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceSocket {
    public static void main(String[] args) throws Exception {
        //s1();
        // s2();
        //s3();
        s4();
    }

    //存在问题：只能运行一次
    public static void s1() throws Exception {
        //获取socket
        ServerSocket serverSocket = new ServerSocket();
        //绑定端口
        serverSocket.bind(new InetSocketAddress(8989));
        //等待响应
        Socket socket = serverSocket.accept();
        //处理响应
        InputStream is = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        while (true) {
            String s = br.readLine();
            if (s == null) break;
            System.out.println("请求数据：" + s);
        }

        System.out.println("---");
        //响应
        String str = "疑似地上霜";
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw.println(str);
        pw.flush();

        System.out.println("关闭资源");
        //关闭资源
        socket.shutdownOutput();
        pw.close();
        socket.close();
    }

    //存在问题:单线程效率低
    public static void s2() throws Exception {
        //获取socket
        ServerSocket serverSocket = new ServerSocket();
        //绑定端口
        serverSocket.bind(new InetSocketAddress(8989));
        while (true) {
            //等待响应
            Socket socket = serverSocket.accept();
            //处理响应
            InputStream is = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            while (true) {
                String s = br.readLine();
                if (s == null) break;
                System.out.println("请求数据：" + s);
            }

            System.out.println("---");
            //响应
            String str = "疑似地上霜";
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw.println(str);
            pw.flush();

            System.out.println("关闭资源");
            //关闭资源
            socket.shutdownOutput();
            pw.close();
            socket.close();
        }
    }

    //存在问题：资源利用率较低
    public static void s3() throws Exception {
        //获取socket
        final ServerSocket serverSocket = new ServerSocket();
        //绑定端口
        serverSocket.bind(new InetSocketAddress(8989));
        ExecutorService pool = Executors.newFixedThreadPool(150);
        while (true) {
            pool.submit(new Callable<Object>() {
                public Object call() throws Exception {

                    //等待响应
                    Socket socket = serverSocket.accept();
                    //处理响应
                    InputStream is = socket.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(reader);
                    while (true) {
                        String s = br.readLine();
                        if (s == null) break;
                        System.out.println("请求数据：" + s);
                    }

                    System.out.println("---");
                    //响应
                    String str = "疑似地上霜";
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    pw.println(str);
                    pw.flush();

                    System.out.println("关闭资源");
                    //关闭资源
                    socket.shutdownOutput();
                    pw.close();
                    socket.close();
                    return null;
                }
            });
        }
    }

    //卖狗肉
    public static void s4() throws Exception {
        //获取socket
        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8989));
        ExecutorService pool = Executors.newFixedThreadPool(150);
        while (true) {
            pool.submit(new Callable<Object>() {
                public Object call() throws Exception {

                    //等待响应
                    SocketChannel socket = serverSocketChannel.accept();
                    //结收数据
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    while (true) {
                        buffer.clear();
                        int read = socket.read(buffer);
                        if (read == -1) break;
                        outputStream.write(buffer.array(), 0, read);
                    }
                    System.out.println("接收到的数据" + new String(outputStream.toByteArray()));
                    //响应
                    String res = "闺中少妇不知愁";
                    socket.write(ByteBuffer.wrap(res.getBytes()));
                    socket.shutdownOutput();
                    socket.close();

                    return null;
                }
            });
        }
    }


}
