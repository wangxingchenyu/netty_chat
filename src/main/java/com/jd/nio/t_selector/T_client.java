package com.jd.nio.t_selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Author: wzl
 * @Date: 2020/1/3 9:05
 */
public class T_client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);

        // 执行连接
        if (!socketChannel.connect(inetSocketAddress)) { // 如果没有连接成功
            while (!socketChannel.finishConnect()) { // 一直不停的连接
                System.out.println("尝试连接中................");
            }
        }

        //String content = "Selector Client";
        //ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
        //socketChannel.write(byteBuffer);
        //System.in.read();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String content = scanner.nextLine();
            // wrap方法，直接将字节数组放入进去
            ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes());
            socketChannel.write(byteBuffer);
        }
    }
}
