package com.ad.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:44
 *
 * @version 1.0
 */
@Entity
@Data
public class TagInfo {
    @Id
    private Integer tagId;

    /*内容*/
    private String tagContent;

}
