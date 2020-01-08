package com.jd.heart_beat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:24
 * @Version 1.0
 */
public class ServerListenerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception { // 读取数据
        // 在此执行消息转发
        channelGroup.forEach(channel -> {
            if (channel == ctx.channel()) { // 发送给自己
                channel.writeAndFlush("[我自己] " + msg);
            } else { // 发送给其它的客户端
                channel.writeAndFlush("客户端:" + channel.remoteAddress() + "说: " + msg);
            }
        });
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // 增加handler的时候
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端:[" + channel.remoteAddress() + "]" + "加入聊天室");
        channelGroup.add(channel);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // 减少handler的时候
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(channel.remoteAddress() + "离开聊天室");
        System.out.println(channelGroup.size());  // 该方法会自动的删除掉这个离开的channel
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {   // 数据读取完成
        // super.channelReadComplete(ctx);
        System.out.println("数据读取完成............");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception { // 空闲状态监听
        //super.userEventTriggered(ctx, evt);
        IdleStateEvent event = (IdleStateEvent) evt;
        String eventDesc = null;
        switch (event.state()) {
            case READER_IDLE:
                eventDesc = "reading idle";
                break;
            case WRITER_IDLE:
                eventDesc = "writer idle";
                break;
            case ALL_IDLE:
                eventDesc = "all idle";
                break;
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(time + " 状态: " + eventDesc);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        System.out.println(cause.getMessage());
        ctx.channel().close();
    }
}
