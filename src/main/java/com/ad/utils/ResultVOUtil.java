package com.ad.utils;

import com.ad.VO.ResultVO;

/**
 * Create By  @林俊杰
 * 2020/8/8 22:38
 *
 * @version 1.0
 */
public class ResultVOUtil {

    public static ResultVO build(Integer status, String msg, Object data){
        return new ResultVO(status,msg,data);
    }

    public static ResultVO success(Object data) {
        return new ResultVO<Object>(200,"OK",data);
    }

    public static ResultVO success(){
        return success(null);
    }


    public static ResultVO errorMsg(String msg) {
        return new ResultVO(500, msg, null);
    }

    public static ResultVO errorMap(Object data) {
        return new ResultVO<Object>(501, "error", data);
    }

    public static ResultVO errorTokenMsg(String msg) {
        return new ResultVO(502, msg, null);
    }

    public static ResultVO errorException(String msg) {
        return new ResultVO(555, msg, null);
    }


}
