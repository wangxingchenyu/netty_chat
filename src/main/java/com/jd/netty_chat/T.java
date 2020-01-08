package com.jd.netty_chat;

import io.netty.util.NettyRuntime;

import java.io.IOException;

/**
 * @Author: wzl
 * @Date: 2020/1/6 13:44
 */
public class T {
    public static void main(String[] args) throws IOException {
        int i = NettyRuntime.availableProcessors();

        System.out.println("Cpu 核数:" +i);
    }
}
