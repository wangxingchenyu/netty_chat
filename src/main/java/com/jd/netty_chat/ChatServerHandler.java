package com.jd.netty_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


    private static ChannelGroup channels1 = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        EventExecutor instance = GlobalEventExecutor.INSTANCE;
        Channel selfChannel = ctx.channel();
        System.out.println("发送过来的通道: " + selfChannel.toString());
        System.out.println("------" + selfChannel.remoteAddress().toString().substring(1) + "-发来数据: " + s);
        String message = "[+" + selfChannel.remoteAddress().toString().substring(1) + "+ 说:]" + s;

        // 将耗时长的任务放到taskQueue里来进行异步处理
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(1000 * 5);
                selfChannel.writeAndFlush("5秒后的数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 将耗时长的任务放到taskQueue里来进行异步处理
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(1000 * 10);
                selfChannel.writeAndFlush("15秒后的数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ctx.channel().eventLoop().schedule(() -> {
            selfChannel.writeAndFlush("延时队列里面的消息");
        }, 5, TimeUnit.SECONDS);


        for (Channel channel : channels) {
            if (channel != selfChannel) {
                channel.writeAndFlush(message);
            }
        }
        System.out.println("go on...............");
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("读取完成");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        Channel channel2 = ctx.pipeline().channel();
        ChannelHandler handler = ctx.handler();
        System.out.println("channel" + channel.hashCode());
        System.out.println("handler" + handler.hashCode());

        System.out.println("线程: " + Thread.currentThread().getName());
        //ctx.pipeline().channel().eventLoop().execute();

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
