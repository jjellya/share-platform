package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/21 18:07
 *
 * @version 1.0
 */
@Getter
public enum SQLEnum {
    POST_TYPE(0,"话题类型"),

    DOC_TYPE(1,"文件类型"),
    ;

    private Integer code;

    private String message;

    SQLEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}