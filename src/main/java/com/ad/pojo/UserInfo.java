package com.ad.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:23
 *
 * @version 1.0
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String userId;

    private String openId;

    private String sessionKey;

    /*用户名称nick_name*/
    private String userName;

    /*用户头像*/
    private String avatarUrl;

    /*性别   0 男 1 女*/
    private Integer userGender;

    /*年级*/
    private Integer userGrade;

    /*Star获得数*/
    private Integer userStar;

    /*上传资源数量*/
    private Integer docNum;

    /*发表帖子数量*/
    private Integer postNum;

    /*用户E-mail*/
    private String userMail;
}
