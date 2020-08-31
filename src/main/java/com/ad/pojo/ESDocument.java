package com.ad.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create By  @林俊杰
 * 2020/8/30 17:00
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ESDocument {

    private String postTitle;

    private String postContent;

    private String tag1;

    private String tag2;

    private String tag3;

    private String docName;

    private Date updateTime;

    private String userName;

    private int postId;

    private int userId;

    private int docId;

}
