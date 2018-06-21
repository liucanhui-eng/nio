package com.baizhi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioDemo{
    public static void main( String[] args )throws IOException{
        FileInputStream in=new FileInputStream("D:\\KuGou\\董贞 - 爱殇.mp3");
        FileOutputStream out=new FileOutputStream("F:\\tomcat\\董贞 - 爱殇.mp3");

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true){
            byteBuffer.clear();
            int read = inChannel.read(byteBuffer);
            if (read==-1)break;
            //byteBuffer.flip();
            outChannel.write(byteBuffer);
        }
        in.close();
        out.close();



    }
    public static void  showStatus(String mes, ByteBuffer byteBuffer){
        System.out.println("mes position  limit  =====\t" +mes+byteBuffer.position()+"\t"+byteBuffer.limit()+"\t"+byteBuffer.capacity() +"\n\n");
    }
}
