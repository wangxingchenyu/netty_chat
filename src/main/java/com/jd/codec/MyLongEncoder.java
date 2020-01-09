package com.jd.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: wzl
 * @Date: 2020/1/8 13:51
 * 由于发送的数据
 */
public class MyLongEncoder extends MessageToByteEncoder<Long> { // 编码器

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongEncoder 被调用,数据为: " + msg);
        out.writeLong(msg);
    }
}
