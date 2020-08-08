package com.ad.exception;

import com.ad.enums.ResultEnum;

/**
 * Create By  @林俊杰
 * 2020/8/1 20:28
 *
 * @version 1.0
 */
public class UserException extends RuntimeException{

    private Integer code;

    public UserException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code=resultEnum.getCode();
    }

    public UserException(Integer code,String message) {
        super(message);

        this.code = code;
    }
}
