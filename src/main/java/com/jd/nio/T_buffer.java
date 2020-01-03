package com.jd.nio;

import java.nio.IntBuffer;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/31 10:19
 * @Version 1.0
 */
public class T_buffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        int capacity = intBuffer.capacity();

        // 放入Buffer中
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        // 取出来,取出来之前必须flip一下，目标是将buffer写到写的模式
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            int i = intBuffer.get();
            System.out.println(i);
        }


    }
}
