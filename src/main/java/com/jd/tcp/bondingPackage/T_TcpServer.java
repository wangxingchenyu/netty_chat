package com.jd.tcp.bondingPackage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:01
 */
public class T_TcpServer {
    int port;

    public T_TcpServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new T_TcpServer(9998).run();
    }

    public void run() {
        EventLoopGroup boosgroup = new NioEventLoopGroup();
        EventLoopGroup workergroup = new NioEventLoopGroup();
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
                            pipeline.addLast(new T_TcpServerHandler());

                        }
                    });

            System.out.println("Netty Chat Server 启动.....");

            ChannelFuture future = b.bind(port).sync();

            // 监听器
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
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
        }
    }
}
