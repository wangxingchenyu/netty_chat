package com.jd.tcp.bondingPackage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:06
 */
public class T_TcpClient {
    private String host;
    private int port;

    public T_TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new T_TcpClient("localhost", 9998).run();
    }

    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() { //直接是handler，不需要childHandler
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new T_TcpClientHandler());
                        }
                    });
            // 执行连接操作,自身连接的Channel
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            Channel channel = cf.channel();
            System.out.println("-------" + channel.localAddress().toString().substring(1) + "-----------");
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                channel.writeAndFlush(s + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
