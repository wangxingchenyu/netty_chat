package com.jd.nio.copyFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2020/1/2 11:13
 * @Version 1.0
 * <p>
 * 读写两个操作都会让buffer里面的指针向前走 -> -> -> 这样的过程
 */
public class T_copyFile {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("d:/two.txt");
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("d:/three.txt");
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        int flag = 0;
        while ((flag = fileInputStreamChannel.read(byteBuffer)) != -1) {  //  channel读的时候，相当于往装数据
            byteBuffer.clear(); // 清空buffer里面的数据,写入之前执行这操作      limit = position; position = 0;
            fileOutputStreamChannel.write(byteBuffer);  // fileOutputStreamChannel写的时候，相当于往channel里面取数据
            byteBuffer.flip();  // position = 0 ；limit = capacity;
            // 这里面的clear 与flip 作用相同，buffer里面是否能存数据或者能存多少的数据，就看position,limit,capacity
        }

        fileInputStream.close();
        fileOutputStream.close();

    }
}
