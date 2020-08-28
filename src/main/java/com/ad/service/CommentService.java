package com.ad.service;

import com.ad.pojo.CommentInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 12:10
 *
 * @version 1.0
 */
public interface CommentService {

    CommentInfo addComment(int postId,int userId,int docId,int type,String content);

    CommentInfo findOneById(int id);

    List<CommentInfo> findByContent(String content);

    List<CommentInfo> findByPostId(int postId);

    List<CommentInfo> findByUserId(int userId);

    List<CommentInfo> findByDocId(int docId);

    int update(CommentInfo commentInfo);

    int deleteById(int id);

}
