package com.ad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/30 22:07
 *
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocShowDTO {

    private Integer docId;

    /*文件名称*/
    private String docName;

    /*图片内容*/
    private String img;

    /*上传用户名称*/
    private String username;

    /*上传用户头像*/
    private String avatarUrl;

    /*收藏数量*/
    private Integer starNum;

    /*标签内容*/
    private List<String> tag;

    /*最近上传时间*/
    private String uploadTime;
}
