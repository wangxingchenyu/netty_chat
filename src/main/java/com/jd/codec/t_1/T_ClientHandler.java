package com.jd.codec.t_1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:06
 */
public class T_ClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("客户端收到了: " + msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // 连接上的时候传送给服务端
        // ctx.writeAndFlush(Unpooled.copiedBuffer("012345670123456701234567", CharsetUtil.UTF_8));
        System.out.println("执行发送.............");
        ctx.writeAndFlush(100000000000L);
    }
}
