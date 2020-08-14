package com.ad.VO;

import com.sun.istack.Nullable;
import lombok.Data;

/**
 * Create By  @林俊杰
 * 2020/8/8 21:28
 *
 * @version 1.0
 */
@Data
public class ResultVO<T> {

        private Integer status;

        private String msg;

        private T data;


    public ResultVO(@Nullable Integer status,
                    @Nullable String msg,
                    @Nullable T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

//    public boolean isOK(){
//        return this.status==200;
//    }

}
