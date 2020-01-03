package com.jd.nio.zeroCopy.old;

import java.io.*;
import java.net.Socket;

/**
 * @Author: wzl
 * @Date: 2020/1/3 15:04
 */
public class T_client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 9999);

        FileInputStream fileInputStream = new FileInputStream("d:/apache-maven-3.6.0-bin.zip");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        // 然后从 fileInputStream  读到 dataOutputStream 然后输出
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = fileInputStream.read(buffer)) != -1) {
            total += readCount;
            // 放入到 dataOutputStream 里面，由于 dataOutputStream 包裹的是 socket.getOutStream() 所以 等于向socket里面写
            dataOutputStream.write(buffer);
        }
        long endTime = System.currentTimeMillis();

        // 必须关闭
        fileInputStream.close();
        dataOutputStream.close();

        System.out.println("发送字节数:" + total + "耗时:" + (endTime - startTime));

    }
}
