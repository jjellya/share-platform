package com.ad.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:52
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagLink {
    @Id
    private Integer tlinkId;

    /*标签ID*/
    private Integer tagId;

    /*文档ID*/
    private Integer docId;

    /*帖子ID*/
    private Integer postId;
}
