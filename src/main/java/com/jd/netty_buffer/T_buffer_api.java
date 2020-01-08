package com.jd.netty_buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Author: wzl
 * @Date: 2020/1/7 9:32
 */
public class T_buffer_api {
    public static void main(String[] args) {
        // Charset charset = Charset.forName("utf-8");
        // Charset utf8 = CharsetUtil.UTF_8;  两种方式一样,编码
        ByteBuf buf = Unpooled.copiedBuffer("BufferContent", CharsetUtil.UTF_8);

        if (buf.hasArray()) {  // 上面在放入到buffer里面用utf8,所以在 new String 里面也要以同样的编码方式来解码
            byte[] bytes = buf.array(); // 获取字节数组
            System.out.println(new String(bytes, CharsetUtil.UTF_8)); // 解码

            //  System.out.println(buf); // 默认情况下 cap是buffer里内容的3倍 -- UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 13, cap: 39)

            // 提前读一次
            byte b = buf.readByte(); // 只有这样的读，才能使用readerIndex指针移   byte aByte = buf.getByte(0); 这样的方式不行


            System.out.println("arrayOffset: " + buf.arrayOffset());    // arrayOffset
            System.out.println("readerIndex: " + buf.readerIndex());    //  readerIndex
            System.out.println("writerIndex: " + buf.writerIndex());    //  writerIndex
            System.out.println("capacity: " + buf.capacity());       //  容量
            System.out.println("可读的字节数: " + buf.readableBytes());  // 可读的字节数 就是从readerIndex - writerIndex之前的数据


            // 获取buffer里面一段内容
            System.out.println(buf.getCharSequence(0, 4, CharsetUtil.UTF_8));  // Buff

        }

    }
}
