package com.jd.nio.scatteringAndGathering;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author: wzl
 * @Date: 2020/1/2 14:15
 * <p>
 * 类似于Hadoop MapperReduce
 */
public class T_scattringAndGathering {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);

        // SocketAddress- abstract
        //  - InetSocketAddress - extends
        serverSocketChannel.socket().bind(inetSocketAddress);
        System.out.println("serverSocketChannel started success");

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();


        long flag = 0L;  // socketChannel.read 可以放一个byteBuffers数组
        while ((flag = socketChannel.read(byteBuffers)) != -1) {
            //Arrays.asList(byteBuffers).stream().forEach(buffer -> buffer.clear());

            Arrays.asList(byteBuffers).stream().map(buffer -> "Read: " + "position=" + buffer.position() + " limit=" + buffer.limit()).forEach(System.out::println);

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip()); // 重置position
            // 查看buffer状态

            socketChannel.write(byteBuffers);
            Arrays.asList(byteBuffers).stream().map(buffer -> "Write: " + "position=" + buffer.position() + " limit=" + buffer.limit()).forEach(System.out::println);
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear()); // 重置position
        }

    }
}
