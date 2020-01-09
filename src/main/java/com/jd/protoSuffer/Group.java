package com.jd.protoSuffer;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: wzl
 * @Date: 2020/1/8 9:31
 */
@Data
@Builder
@ToString
public class Group {
    private int id;
    private String name;
    private User user;
}
