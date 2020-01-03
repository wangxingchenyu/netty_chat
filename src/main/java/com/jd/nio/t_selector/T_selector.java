package com.jd.nio.t_selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: wzl
 * @Date: 2020/1/2 16:16
 */
public class T_selector {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false); // 设置为非阻塞
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9999);
        serverSocketChannel.socket().bind(inetSocketAddress);

        Selector selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 注入到Selector ,交指定事件为accept事件

        while (true) { // 轮循查看是否有相关的事情发生( 轮循中基于事件驱动操作)
            if (selector.select(1000) == 0) { // 连接1秒就超时，就跳过此操作
                System.out.println("服务器等待1秒，无连接");
                continue;
            }

            // 返回 >0  表示已经有关注的事件了 获取所有selectionKey集合
            // selectedKeys 返回关注的事件,并通过selectionKeys 能查找对应的channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();

            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = selectionKeyIterator.next(); // 获取所有的关注的key

                if (selectionKey.isAcceptable()) { // 询问是否可接受的操作
                    // 如果是这个操作，则执行accept方法
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 读写操作都是通过socketChannel来实现,注入一个读的事件
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    // socketChannel.configureBlocking(false);
                }

                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();  // 获取关注的channel
                    // 获取buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    byteBuffer.clear();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    String s = new String(byteBuffer.array());

                    System.out.println("客户端发来的数据: " + s);
                }

                // 防止得复操作，清处理此事件
                selectionKeyIterator.remove();

            }

        }


    }
}
