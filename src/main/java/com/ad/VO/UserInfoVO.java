package com.ad.VO;

import com.ad.pojo.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Create By  @林俊杰
 * 2020/8/14 23:45
 *
 * @version 1.0
 */
@Data
public class UserInfoVO {


    @JsonProperty("id")
    private Integer id;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("gender")
    private Integer gender;

    @JsonProperty("star")
    private Integer star;


    public UserInfoVO(UserInfo user) {
        this.id  = user.getUserId();
        this.avatarUrl = user.getAvatarUrl();
        this.nickName = user.getUserName();
        this.gender = user.getUserGender();
        this.star = user.getUserStar();
    }
}
