package com.jd.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: wzl
 * @Date: 2020/1/8 13:42
 */
//public class MyLongDecoder extends ByteToMessageDecoder { // long类型解码器
public class MyLongDecoder extends ReplayingDecoder<Void> { // long类型解码器


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // if (in.readableBytes() >= 8) {  // long 8个字节
        out.add(in.readLong());
        // }
        //FixedLengthFrameDecoder
    }
}
