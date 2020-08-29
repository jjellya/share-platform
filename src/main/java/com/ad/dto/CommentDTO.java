package com.ad.dto;

import lombok.Data;

/**
 * @author WenZhikun
 * @data 2020-08-29 10:51
 */
@Data
public class CommentDTO {
    /*评论内容*/
    private String content;

    /*用户头像*/
    private String avatarUrl;

    /*用户名称*/
    private String username;

    /*用户Id*/
    private int userId;

    /*评论Id*/
    private int commentId;

    /*帖子ID*/
    private int postId;

    /*评论时间*/
    private String commentTime;

    /*评论类型*/
    private int commentType;


}
