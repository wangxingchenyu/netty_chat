package com.jd.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/30 9:04
 * @Version 1.0
 */
public class BlockServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        ExecutorService executorService = Executors.newCachedThreadPool();


        System.out.println("服务器启动.....");
        while (true) {
            System.out.println("等待请求中............");
            Socket socket = serverSocket.accept();
            // 创建一个线程去处理这个请求
            executorService.execute(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

        }

    }

    // 单独写一个处理器来处理数据处理
    public static void handler(Socket socket) throws IOException {
        try {
            System.out.println("ThreadId: " + Thread.currentThread().getId() + "ThreadName: " + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            System.out.println("开始读取内容.............");

            int flag;
            // int read = inputStream.read(bytes);
            while ((flag = inputStream.read(bytes)) != -1) {
                String s = new String(bytes, 0, flag);
                System.out.println("ThreadId: " + Thread.currentThread().getId() + "     ThreadName: " + Thread.currentThread().getName());
                System.out.println("输出的结果: " + s + " " + flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

    }

}
