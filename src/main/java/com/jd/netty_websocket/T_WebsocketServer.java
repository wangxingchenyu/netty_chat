package com.jd.netty_websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: wzl
 * @Date: 2020/1/7 13:13
 */
public class T_WebsocketServer {
    private int port;

    public T_WebsocketServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) { // 运行服务端
        T_WebsocketServer t_websocketServer = new T_WebsocketServer(9999);
        t_websocketServer.run();
    }

    public void run() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 基于Http协议，使用Http解码器与编码器
                            pipeline.addLast(new HttpServerCodec());
                            // 协议是通过分块写，所以加入chunk处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // http 协议在数据传输过程中是分段，或者是分块发送的，HttpObjectAggregator实现的功能就是将多块数据进行聚合
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 将http协议转成websocket协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 加入自定义的websocket处理器
                            pipeline.addLast(new T_WebsocketHandler());
                        }
                    });

            System.out.println("Netty Server Start...............");
            // 绑定端口
            ChannelFuture future = serverBootstrap.bind(port).sync();
            // 监听closeFuture事件
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
