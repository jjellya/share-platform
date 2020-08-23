package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/23 18:47
 *
 * @version 1.0
 */
@Getter
public enum DocTypeEnum {
    TXT(1,".txt"),

    DOCX(3,".docx"),
    ;

    private Integer code;

    private String message;

    DocTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
