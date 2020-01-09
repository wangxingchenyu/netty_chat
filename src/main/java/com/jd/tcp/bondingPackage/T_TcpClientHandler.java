package com.jd.tcp.bondingPackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:06
 */
public class T_TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);// 读自己写读的数据

        String s = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("客户端接收到的数据=" + s);
        System.out.println("客户端接接消息总次数=" + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) { // 发送10条数据给服务端
            ByteBuf buf = Unpooled.copiedBuffer("hello Server-" + i + " ", CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
            System.out.println("执行发送........");
        }
    }

}
