package com.jd.nio.copyFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wangzhilei
 * @date 2020-01-02 13:23
 */
public class T_mappdByteBuffer {
    public static void main(String[] args) throws IOException {
        // 创建一个随机访问流
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/one.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(1, (byte) 'b');
        mappedByteBuffer.put(2, (byte) 'c');

        randomAccessFile.close();
        System.out.println("update success");
    }
}
