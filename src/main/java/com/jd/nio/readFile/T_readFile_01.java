package com.jd.nio.readFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2020/1/2 9:59
 * @Version 1.0
 */
public class T_readFile_01 {
    public static void main(String[] args) throws IOException {

        File file = new File("d:/one.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //channel.read(byteBuffer);
        int flag = 0;

        while ((flag = channel.read(byteBuffer)) != -1) {
            byteBuffer.clear(); // 在读的时候,
            String s = new String(byteBuffer.array());
            System.out.println(s);
        }

    }
}
