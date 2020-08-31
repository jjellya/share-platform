package com.ad.dto;

import lombok.Data;

/**
 * @author WenZhikun
 * @data 2020-08-31 10:44
 */
@Data
public class DocDTO {
    /*文件Id*/
    private int docId;

    /*文件名称*/
    private String docName;

    /*上传时间*/
    private String updateTime;

    /*下载次数*/
    private int downloadNum;

    /*star数量*/
    private int starNum;

    /*用户Id*/
    private int userId;

    /*用户头像*/
    private String avartUrl;

    /*用户名*/
    private String userName;
}
