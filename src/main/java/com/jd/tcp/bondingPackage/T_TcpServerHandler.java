package com.jd.tcp.bondingPackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:02
 */
public class T_TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception { // 接收数据
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);// 读自己写读的数据

        String s = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器接收到的数据=" + s);
        System.out.println("服务器接接消息总次数=" + (++this.count));

        for (int i = 0; i < 10; i++) { // 服务端，向客户端发送消息
            ByteBuf buf = Unpooled.copiedBuffer("hello Client-" + i + " ", CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }


    }


}
