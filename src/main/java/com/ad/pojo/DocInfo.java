package com.ad.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:55
 *
 * @version 1.0
 */
@Entity
@Data
public class DocInfo {
    @Id
    private Integer docId;

    /*文件名称*/
    private String docName;

    /*文件类型*/
    private int docType;

    /*文件大小*/
    private int docSize;

    /*文件路径*/
    private String docPath;

    /*下载次数*/
    private int downloadNum;

    /*Star数量*/
    private int starNum;

    /*上传者Id*/
    private Integer userId;

    /*上传时间*/
    private Date uploadTime;
}
