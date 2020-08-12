package com.ad.enums;

import lombok.Getter;

/**
 * Create By  @林俊杰
 * 2020/8/1 20:29
 *
 * @version 1.0
 */
@Getter
public enum ResultEnum {
    USER_LOGIN_ERROR(167,"请先登录"),

    IS_OK(200,"OK"),

    ERROR_MESSAGE(500,"内部服务器错误"),

    ERROR_MAP(501,"请求错误"),

    ERROR_TOKEN_MESSAGE(502,"token错误"),

    ERROR_UNKNOWN(555,"发生未知错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
