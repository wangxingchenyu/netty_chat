package com.jd.tcp;

import com.jd.tcp.defineProto.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author: wzl
 * @Date: 2020/1/8 14:02
 */
public class T_TcpServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception { // 接收数据

        byte[] content = msg.getContent();
        String finalResult = new String(content, Charset.forName("utf-8"));
        System.out.println("读取到客户端的数据: " + finalResult + "获取了:" + (++this.count) + "次");


        System.out.println("---------------------------服务端执行发送--------------------------");

        for (int i = 0; i < 3; i++) {

            String willSendContent = "什么时间去，我也去-" + i;
            byte[] content1 = willSendContent.getBytes(CharsetUtil.UTF_8);
            int length = content1.length;
            MessageProtocol messageProtocol = new MessageProtocol(length, content1);
            ctx.writeAndFlush(messageProtocol);
        }

    }


}
