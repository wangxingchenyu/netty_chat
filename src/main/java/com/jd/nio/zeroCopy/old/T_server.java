package com.jd.nio.zeroCopy.old;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: wzl
 * @Date: 2020/1/3 15:00
 */
public class T_server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true) {
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            FileOutputStream fileOutputStream = new FileOutputStream("d:/maven11.zip");

            try {
                byte[] buffer = new byte[4096];
                while (true) {
                    int readCount = dataInputStream.read(buffer, 0, buffer.length);
                    if (-1 == readCount) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        break;
                    }
                    fileOutputStream.write(buffer, 0, readCount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
