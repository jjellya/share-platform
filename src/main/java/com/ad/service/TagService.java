package com.ad.service;

import com.ad.pojo.TagInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 21:58
 *
 * @version 1.0
 */
public interface TagService {

    TagInfo addTag(String content);

    TagInfo findOneById(Integer id);

    List<TagInfo> findByContent(String content);

    int update(TagInfo tag);

    int deleteById(Integer id);
}
