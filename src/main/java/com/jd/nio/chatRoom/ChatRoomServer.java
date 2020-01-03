package com.jd.nio.chatRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wangzhilei
 * @date 2020-01-03 09:33
 */
public class ChatRoomServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6666;

    public ChatRoomServer() {
        // 初始化处理
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            // serverSocketChannel 注册到 selector中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                // 捕捉有事件的通道的个数,并设置超时，实现非阻塞处理
                int selectCount = selector.select(2000);
                if (selectCount > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) { // 判断是连接事件
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // 设置非阻塞
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            // 并提示哪个客户端上线

                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }

                        if (key.isReadable()) { // 建议可以通讯的通道后，可以判断是否离线
                            SocketChannel channel = null;

                            try {
                                SelectableChannel selectableChannel = key.channel();
                                if (selectableChannel instanceof SocketChannel) {
                                    channel = (SocketChannel) selectableChannel;
                                }
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                                // 断言channel 不为Null
                                assert channel != null;
                                int readCount = channel.read(byteBuffer);
                                if (readCount > 0) {
                                    //StringBuffer stringBuffer = new StringBuffer();
                                    // stringBuffer.append("From客户端: ").append(new String(byteBuffer.array()));
                                    String msg = "来自客户端: " + new String(byteBuffer.array());
                                    System.out.println(msg);

                                    // 执行数据转发 (发送给其它的客户端)
                                    sendMessageToOtherClients(msg, channel);
                                }
                            } catch (IOException e) {

                                System.out.println(channel.getRemoteAddress() + "离线...");

                                // 取消注册
                                key.cancel();
                                // 关闭通道
                                channel.close();
                            }

                        }

                        // 移动掉当前事件的操作
                        iterator.remove();
                    }
                } else {
                    System.out.println("暂无通道连接.........");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToOtherClients(String msg, SocketChannel self) {
        System.out.println("执行转发");
        // 去掉本身
        Set<SelectionKey> keys = selector.keys(); //获取注册上面的通道所有的通道
        for (SelectionKey key : keys) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                try {
                    socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
        for (SelectionKey key : keys) {
            if (key != self) { // 通过key获取channel 来发送数据
                if (key.channel() instanceof SocketChannel && key != self) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                    try {
                        channel.write(byteBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
         */

    }

    public static void main(String[] args) {
        ChatRoomServer chatRoomServer = new ChatRoomServer();
        chatRoomServer.listen();
    }
}
