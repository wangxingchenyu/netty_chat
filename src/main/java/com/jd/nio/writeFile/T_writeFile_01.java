package com.jd.nio.writeFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2020/1/2 9:59
 * @Version 1.0
 */
public class T_writeFile_01 {
    public static void main(String[] args) throws IOException {
        FileOutputStream outputStream = new FileOutputStream("d:/one.txt");
        FileChannel channel = outputStream.getChannel();

        String content = "Hello Nio";
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put(content.getBytes());

        byteBuffer.flip();

        channel.write(byteBuffer);

        outputStream.close();

    }
}
