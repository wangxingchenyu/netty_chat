package com.jd.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:23
 * @Version 1.0
 * Netty 网络框架
 */
public class HttpServer {
    int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new HttpServer(9999).run();
    }

    public void run() {
        EventLoopGroup boosgroup = new NioEventLoopGroup();
        EventLoopGroup workergroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosgroup, workergroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new T_channelInitializer());
            System.out.println("Netty Chat Server 启动.....");
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosgroup.shutdownGracefully();
            workergroup.shutdownGracefully();
            System.out.println("Netty Chat Server 关闭.....");
        }
    }

}
