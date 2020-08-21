package com.ad.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create By  @林俊杰
 * 2020/8/21 18:02
 *
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserScore {
    @Id
    private Integer scoreId;

    /*用户ID*/
    private Integer userId;

    /*话题ID*/
    private Integer postId;

    /*文件ID*/
    private Integer docId;

    /*评分类型*/
    private int scoreType;

    /*评分*/
    private Integer scoreValue;
}
