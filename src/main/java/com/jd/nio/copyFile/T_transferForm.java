package com.jd.nio.copyFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2020/1/2 11:00
 * @Version 1.0
 * <p>
 * 实现文件的拷备
 */
public class T_transferForm {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("d:/a.jpg");
        FileChannel source = fileInputStream.getChannel();


        FileOutputStream fileOutputStream = new FileOutputStream("d:/b.jpg");
        FileChannel dest = fileOutputStream.getChannel();

        // 直接进行文件拷备 CopyFile
        dest.transferFrom(source, 0, source.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
