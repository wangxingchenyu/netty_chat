package com.jd.nio.readFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2020/1/2 9:59
 * @Version 1.0
 */
public class T_readFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("d:/one.txt");

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));



    }
}
