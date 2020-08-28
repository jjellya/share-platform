package com.ad.VO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YibanUserVO {
    @JsonProperty("code")
    private String code;

    @JsonProperty("accesstoken")
    private String accesstoken;

    @JsonProperty("sessionId")
    private String sessionid;

    @JsonProperty("openId")
    private String openId;

    @JsonProperty("userInfo")
    private UserInfoVO userInfoVO;
}
