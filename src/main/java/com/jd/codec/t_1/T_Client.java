package com.jd.codec.t_1;

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
public class T_Client {
    private String host;
    private int port;

    public T_Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new T_Client("localhost", 9999).run();
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
                            //pipeline.addLast(new MyLCodec());
                            pipeline.addLast(new MyLongEncoder());
                            pipeline.addLast(new MyLongDecoder());
                            pipeline.addLast(new T_ClientHandler());
                        }
                    });
            // 执行连接操作,自身连接的Channel
            ChannelFuture cf = bootstrap.connect(host, port).sync();

            Channel channel = cf.channel();
            System.out.println("-------" + channel.localAddress().toString().substring(1) + "-----------");
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLong()) {
                //String msg = scanner.nextLine();
                long l = scanner.nextLong();
                channel.writeAndFlush(l + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
