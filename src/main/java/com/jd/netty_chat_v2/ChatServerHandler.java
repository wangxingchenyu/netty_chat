package com.jd.netty_chat_v2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:24
 * @Version 1.0
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 由于每次产生一个新的客户端的时候都会初始化此类，所以需要保存状态，则要用静态的属性，跟随类走
     */
    public static List<Channel> channels = new ArrayList<Channel>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel selfChannel = ctx.channel();
        System.out.println("发送过来的通道: " + selfChannel.toString());
        System.out.println("------" + selfChannel.remoteAddress().toString().substring(1) + "-发来数据: " + s);
        String message = "[+" + selfChannel.remoteAddress().toString().substring(1) + "+ 说:]" + s;
        for (Channel channel : channels) {
            if (channel != selfChannel) {
                channel.writeAndFlush(message);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.add(channel);
        String ip = channel.remoteAddress().toString().substring(1);
        System.out.println("[Server:]" + ip + " :上线");
        for (Channel channel1 : channels) {
            System.out.println(channel1.toString());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.remove(channel); //  去除此Channel
        String ip = channel.remoteAddress().toString().substring(1);
        System.out.println("[Server:]" + ip + " :离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
