package com.ad.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Create By  @林俊杰
 * 2020/8/8 23:35
 *
 * @version 1.0
 */
@Data
public class WechatUserVO {

    @JsonProperty("skey")
    private String skey;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("userInfo")
    private UserInfoVO userInfoVO;


}
