package com.jd.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author: zhilei.wang
 * @Date: 2019/12/23 16:24
 * @Version 1.0
 * HttpObject 客户端和服务器相互通讯的数据被封装成HttpObject
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {


            System.out.println("msg类型:" + msg.getClass());
            System.out.println("客户端地址:" + ctx.channel().remoteAddress());

            //HttpRequest httpRequest = (HttpRequest) msg;
            //System.out.println(httpRequest.uri());
            //URI uri = new URI(httpRequest.uri());

            // 回复信息给浏览器，利用非池化的类
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello, This Is Netty Web Server", CharsetUtil.UTF_8);

            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            // 将响应内容写回
            ctx.writeAndFlush(response);
        }
    }
}
