package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/19 21:09
 *
 * @version 1.0
 */
@Getter
public enum ClassificationEnum {
    ORDER_BY_TIME(0,"新鲜"),

    ORDER_BY_SYS(1,"推荐"),

    ORDER_BY_MY(2,"我的"),
    ;

    private Integer code;

    private String message;

    ClassificationEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
