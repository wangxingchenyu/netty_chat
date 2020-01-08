package com.jd.netty_websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: wzl
 * @Date: 2020/1/7 13:14
 */
public class T_WebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 声明一个全局的通道组
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel selfChannel = ctx.channel();
        String content = msg.text();
        channelGroup.forEach(channel -> {
            if (channel == selfChannel) {
                channel.writeAndFlush(new TextWebSocketFrame("我自己:" + content));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("客户:" + channel.remoteAddress() + "说: " + content));
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("客户:" + channel.remoteAddress() + "加入!"));
        channelGroup.add(channel);
        System.out.println("HandlerAdd-客户端的个数: " + channelGroup.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame("客户:" + channel.remoteAddress() + "离开!"));
        System.out.println("HandlerRemove-客户端的个数: " + channelGroup.size());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.channel().closeFuture().sync();
    }
}
