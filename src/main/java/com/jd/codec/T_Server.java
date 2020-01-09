package com.jd.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:01
 */
public class T_Server {
    int port;

    public T_Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new T_Server(9999).run();
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
                            // pipeline.addLast(new MyLCodec());  // 这样的方式，既可以放一个decoder也可以 放一个encoder
                            pipeline.addLast(new MyLongDecoder()); // 解码器传给后面一个handler去处理数据
                            pipeline.addLast(new MyLongEncoder());
                            pipeline.addLast(new T_ServerHandler());

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
