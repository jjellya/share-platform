package com.ad.dto;

import lombok.Data;

/**
 * @author WenZhikun
 * @data 2020-09-02 15:15
 */
@Data
public class CommentDocDTO {
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

    /*文件Id，没有文件默认为0*/
    private int docId;

    /*资源名称*/
    private String docName;

    /*资源类型*/
    private int docType;

    /*是否收藏*/
    private boolean isStar;

    /*star数量*/
    private int starNum;

    /*下载次数*/
    private int downloadNum;
}
