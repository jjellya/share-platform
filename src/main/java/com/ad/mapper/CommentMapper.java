package com.ad.mapper;

import com.ad.pojo.CommentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 11:13
 *
 * @version 1.0
 */
@Mapper
public interface CommentMapper {

    /**
     * 通过ID查找comment
     * @param id
     * @return
     */
    public CommentInfo getCommentById(Integer id);

    /**
     * 通过内容查找comment
     * @param content
     * @return
     */
    public List<CommentInfo> getCommentByContent(String content);


    /**
     * 通过postID查找tag
     * @param postId
     * @return
     */
    public List<CommentInfo> getCommentByPostId(Integer postId);

    /**
     * 通过userId查找tag
     * @param userId
     * @return
     */
    public List<CommentInfo> getCommentByUserId(Integer userId);

    /**
     * 通过docID查找tag
     * @param docId
     * @return
     */
    public List<CommentInfo> getCommentByDocId(Integer docId);

    /**
     * 添加Comment
     * @param comment
     * @return
     */
    public int addComment(CommentInfo comment);

    /**
     * 更新comment
     * @param comment
     * @return
     */
    public int updateComment(CommentInfo comment);

    /**
     * 删除指定id的comment列
     * @param id
     * @return
     */
    public int deleteCommentById(Integer id);

}
