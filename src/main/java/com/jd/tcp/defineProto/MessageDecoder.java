package com.jd.tcp.defineProto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: wzl
 * @Date: 2020/1/9 11:40
 */
public class MessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MessageDecoder 执行了...........");
        int length = in.readInt();

        //ByteBuf buf = in.readBytes(length);
        byte[] bytes = new byte[length];
        in.readBytes(bytes); // 执行读取  这个过程相当于给bytes数组里面填写内容

        MessageProtocol messageProtocol = new MessageProtocol(length, bytes);

        // 封装好后数据，装给下一个handler去处理
        out.add(messageProtocol);

    }
}
