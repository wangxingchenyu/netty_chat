package com.jd.netty_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:23
 * @Version 1.0
 * Netty 网络框架
 */
public class ChatServer {
    int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ChatServer(9999).run();
    }

    public void run() {
        EventLoopGroup boosgroup = new NioEventLoopGroup();
        EventLoopGroup workergroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosgroup, workergroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //  childX表示workGroup  非child开头的表示boosGroup
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("Netty Chat Server 启动.....");

            ChannelFuture future = b.bind(port).sync();
            // future.channel().closeFuture().sync();

            // 监听器
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println(future.isDone());
                        System.out.println(future.isCancelled());
                        System.out.println("监听了 9999 .................");
                    }
                }
            });

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
