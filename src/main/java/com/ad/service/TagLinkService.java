package com.ad.service;


import com.ad.pojo.TagLink;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/20 20:46
 *
 * @version 1.0
 */
public interface TagLinkService {

    TagLink addTagLinkToPost(Integer tagId,Integer postId);

    TagLink addTagLinkToDoc(Integer tagId,Integer docId);

    List<TagLink> findByPostId(Integer postId);

    List<TagLink> findByTagId(Integer tagId);

    List<TagLink> findByDocId(Integer docId);

    int update(TagLink tagLink);

    int deleteById(Integer id);
}
