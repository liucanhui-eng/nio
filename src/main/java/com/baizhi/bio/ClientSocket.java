package com.baizhi.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {
    public static void main(String[] args) throws Exception{
        m1();

    }
    public static  void m1()throws Exception{
        //获取客户端的socket
        Socket socket=new Socket();
        //绑定id和端口
        socket.connect(new InetSocketAddress("localhost",8989));
        //发送请求
        String str="床前明月光";
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw.println(str);
        pw.flush();
        socket.shutdownOutput();

        //关闭资源
        //接收响应
        InputStream is = socket.getInputStream();
        InputStreamReader reader=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(reader);
        while (true){
            String s = br.readLine();
            if (s==null)break;
            System.out.println("接收数据："+s);
        }
        pw.close();
        socket.close();
    }

}
