package com.jd.protoSuffer;

import java.util.Arrays;

/**
 * @Author: wzl
 * @Date: 2020/1/8 9:58
 */
public class T_p {
    public static void main(String[] args) {
        User user = User.builder().id("1").age(20).name("张三").desc("xx").build();
        //创建一个Group对象
        Group group = Group.builder().id(1).name("分组1").user(user).build();

        // 序列化
        byte[] data = ProtostuffUtils.code(group);
        String s = Arrays.toString(data);
        System.out.println(s);

        // 反序列
        Group decode = ProtostuffUtils.decode(data, Group.class);
        System.out.println(decode);

        /**
         *  Netty序列化
         */
        //ByteToMessageDecoder
        //StringEncoder
        //ObjectEncoder
    }
}
