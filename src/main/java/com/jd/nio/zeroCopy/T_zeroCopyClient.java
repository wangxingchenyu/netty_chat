package com.jd.nio.zeroCopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: wzl
 * @Date: 2020/1/6 10:55
 */
public class T_zeroCopyClient {
    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        String fileName = "D:/apache-maven-3.6.0-bin.zip";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        FileChannel fileChannel = fileInputStream.getChannel();

        long startTime = System.currentTimeMillis();

        // 执行0拷备transferTo方法
        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        long endTime = System.currentTimeMillis();

        System.out.println("发送了字节数为:" + fileChannel.size() + "耗时:" + (endTime - startTime));

        fileChannel.close();
    }
}
