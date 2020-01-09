package com.jd.protoSuffer;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wzl
 * @Date: 2020/1/8 9:30
 */
@Data
@Builder
public class User {
    private String id;
    private String name;
    private Integer age;
    private String desc;

}
