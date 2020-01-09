package com.jd.tcp.defineProto;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: wzl
 * @Date: 2020/1/9 11:32
 * 自定义传输协议,所谓协议，就是输出过程中数据的整体结构
 */
@Data
@ToString
public class MessageProtocol {
    private int contentLength; // 定义传输出数据大小
    private byte[] content;    // 定义传输的数据

    public MessageProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }
}
