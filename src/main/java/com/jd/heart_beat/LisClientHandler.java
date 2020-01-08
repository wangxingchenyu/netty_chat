package com.jd.heart_beat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:25
 * @Version 1.0
 */
public class LisClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        // 只执行读取数据
        System.out.println(s.trim());
    }
}
