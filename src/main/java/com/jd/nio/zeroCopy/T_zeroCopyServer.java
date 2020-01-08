package com.jd.nio.zeroCopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: wzl
 * @Date: 2020/1/3 13:41
 */
public class T_zeroCopyServer {
    public static void main(String[] args) throws IOException {
        /**
         * 零拷备
         */
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(inetSocketAddress);


        while (true) {
            System.out.println("监听数据中...........");
            SocketChannel socketChannel = serverSocketChannel.accept();

            handlerFile(socketChannel);

        }

    }

    /**
     * 注意: 用byteBuffer 处理数据的时候一定要注意position的位置，及时的操作clear,flip,或者是
     *
     * @param socketChannel
     * @throws IOException
     */
    public static void handlerFile(SocketChannel socketChannel) throws IOException {

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        int readCount = socketChannel.read(byteBuffer);
        int flag = 0;
        while (true) {
            int read = socketChannel.read(byteBuffer);
            if (read > 0) {
                byteBuffer.clear();
                System.out.println("读取到的字节数:" + flag);
                socketChannel.read(byteBuffer);
                byteBuffer.flip();
                // byteBuffer.rewind();
            } else {
                socketChannel.close();
                break;
            }

        }
    }

}
