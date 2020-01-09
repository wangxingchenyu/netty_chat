package com.jd.tcp;

import com.jd.tcp.defineProto.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:06
 */
public class T_TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        byte[] content = msg.getContent();
        String finalResult = new String(content, Charset.forName("utf-8"));
        System.out.println("读取到服务端的数据: " + finalResult + "获取了:" + (++this.count) + "次");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 3; i++) {
            String willSendContent = "海底捞-" + i;
            byte[] content = willSendContent.getBytes(CharsetUtil.UTF_8);
            int length = content.length;
            MessageProtocol messageProtocol = new MessageProtocol(length, content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

}
