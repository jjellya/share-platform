package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/21 19:32
 *
 * @version 1.0
 */
@Getter
public enum ScoreEnum {
    CLICK(1,"点击+1分"),

    REPLY(2,"评论+2分"),

    STAR(3,"收藏+3分"),
    ;

    private Integer code;

    private String message;

    ScoreEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
