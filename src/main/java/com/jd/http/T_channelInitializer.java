package com.jd.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: wzl
 * @Date: 2020/1/6 17:01
 */
public class T_channelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec()); // 添加http编码器 code decode
        pipeline.addLast("MyServerHandler", new HttpServerHandler());

    }
}
