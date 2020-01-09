package com.jd.codec.cc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @Author: wzl
 * @Date: 2020/1/8 15:18
 *
 * 自定义的方式来编码,这种方式，既可以编码，也同时可以解码
 *
 * encode
 *
 */
public class MyLCodec extends ByteToMessageCodec<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        // 编码
        System.out.println("MyLongEncoder 被调用,数据为: " + msg);
        out.writeLong(msg); // 直接写入即可
        // 其它的编解码器
        //LineBasedFrameDecoder  // 以行尾的分割符 \r或者\n
        // DelimiterBasedFrameDecoder // 以自定义的分割符来解码
        //HttpObjectEncoder/Decoder  根据http消息结构来编解码
        //LengthFieldBasedFrameDecoder  // 根据指定的消息包的长度来分割消息包，这样可以自动处理粘包与拆包的问题
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 解码
        if (in.readableBytes() >= 8) {
            out.add(in.readLong()); // 读取long类型数据放入进去
        }
    }
}
