package com.jd.netty_chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:25
 * @Version 1.0
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("执行了接收...........");
        System.out.println(s.trim());
    }
}
