package com.jd.netty_buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: wzl
 * @Date: 2020/1/7 9:10
 * Netty的BufferBuf 要NIO算法不一样，不需要flip或者clear
 * 读的空间是 readIndex - writeIndex
 * 写的空间   writeIndex - capacity
 */
public class T_buffer {
    public static void main(String[] args) {
        // 放入一个容量为10缓冲
        ByteBuf buffer = Unpooled.buffer(10);

        // 循环10次一次放入
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        // 获取容量
        System.out.println(buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
            // 根据索引来取值
            // byte aByte = buffer.getByte(i);
            byte b = buffer.readByte();
            System.out.println(b);
        }

        System.out.println("execute success");

    }
}
