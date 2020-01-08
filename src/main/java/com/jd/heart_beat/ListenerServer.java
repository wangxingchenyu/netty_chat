package com.jd.heart_beat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:23
 * @Version 1.0
 * Netty 网络框架
 */
public class ListenerServer {
    int port;

    public ListenerServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ListenerServer(9999).run();
    }

    public void run() {
        EventLoopGroup boosgroup = new NioEventLoopGroup();
        EventLoopGroup workergroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosgroup, workergroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 指定日志，并指定日志输出级别
                    .option(ChannelOption.SO_BACKLOG, 128) //  childX表示workGroup  非child开头的表示boosGroup
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());  // 解码
                            pipeline.addLast("encoder", new StringEncoder());  // 编码
                            /**
                             * IdleStateHandler Netty提供的空闲监听的处理器
                             *long readerIdleTime  读空闲时间 如果客户端未发送消息给服务端，则出现写空闲状态
                             *long writerIdleTime  写空闲时间,如果服务端未发送消息给客户端，则属性写空闲状态
                             *long allIdleTime     读写空闲时间.....
                             *
                             * 通过触发下一个handler来执行空闲事件监听
                             */
                            pipeline.addLast(new IdleStateHandler(10, 5, 13, TimeUnit.SECONDS));
                            pipeline.addLast(new ServerListenerHandler()); // 真正执行空闲监听的handler 处理器
                        }
                    });
            System.out.println("Netty Chat Server 启动.....");

            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally { //优雅的关闭
            boosgroup.shutdownGracefully();
            workergroup.shutdownGracefully();
            System.out.println("Netty Chat Server 关闭.....");
        }
    }

}
