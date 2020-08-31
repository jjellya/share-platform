package com.ad.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create By  @林俊杰
 * 2020/7/31 21:01
 *
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LinkInfo {
    @Id
    private Integer linkId;

    private Integer docId;

    private Integer userId;
}
