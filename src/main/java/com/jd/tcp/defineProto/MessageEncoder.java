package com.jd.tcp.defineProto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: wzl
 * @Date: 2020/1/9 11:35
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageEncoder 执行了............");
        // 写入消息的长度 首先放入长度，然后供服务端来根据长度来读取
        out.writeInt(msg.getContentLength());
        // 写入内容
        out.writeBytes(msg.getContent());
    }
}
