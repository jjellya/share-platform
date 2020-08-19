package com.ad.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:36
 *
 * @version 1.0
 */
@Entity
@Data
public class PostInfo {

    @Id
    private Integer postId;

    /*标题*/
    private String postTitle;

    /*内容*/
    private String postContent;

    /*标签1*/
    private String postTag1;

    /*标签2*/
    private String postTag2;

    /*评论数*/
    private Integer commentNum;

    /*帖主ID*/
    private Integer userId;

    /*创建时间*/
    private Date createTime;

    /*最近回复时间*/
    private Date updateTime;

}
