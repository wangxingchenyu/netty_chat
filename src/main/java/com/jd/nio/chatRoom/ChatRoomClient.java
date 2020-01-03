package com.jd.nio.chatRoom;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: wzl
 * @Date: 2020/1/3 9:32
 */
public class ChatRoomClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private String username;

    public ChatRoomClient() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
            socketChannel.configureBlocking(false); // 设置非阻塞
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ready..............");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() { // 提示不要显示创建线程，建议用线程池，暂时忽略
        new Thread(() -> {
            while (true) {
                try {
                    // 判断是否有事件产生 accept,read,write
                    int select = selector.select(2000);
                    if (select > 0) {
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            channel.read(byteBuffer);
                            System.out.println(new String(byteBuffer.array()));
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String info) { // 执行发送信息
        info = username + "说: " + info;
        System.out.println(info);
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatRoomClient chatRoomClient = new ChatRoomClient();
        chatRoomClient.readMessage();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            chatRoomClient.sendMessage(s);
        }
    }
}
