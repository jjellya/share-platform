package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/28 12:20
 *
 * @version 1.0
 */
@Getter
public enum CommentTypeEnum {
    PLAIN_TEXT(0,"纯文本"),

    WITH_DOC(1,"附文件"),
    ;

    private Integer code;

    private String message;

    CommentTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
