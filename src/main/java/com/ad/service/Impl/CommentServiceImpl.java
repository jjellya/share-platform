package com.ad.service.Impl;

import com.ad.enums.CommentTypeEnum;
import com.ad.mapper.CommentMapper;
import com.ad.pojo.CommentInfo;
import com.ad.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 12:11
 *
 * @version 1.0
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentInfo addComment(int postId, int userId, int docId, int type, String content) {
        CommentInfo comment = new CommentInfo();

        comment.setPostId(postId);
        comment.setCommentType(type);
        comment.setUserId(userId);
        if (type == CommentTypeEnum.WITH_DOC.getCode()){
            comment.setDocId(docId);
        }
        comment.setCommentContent(content);
        comment.setCommentTime(new Date());

        try {
            commentMapper.addComment(comment);
        }catch (Exception e){
            log.error("用户ID = "+userId+"添加评论发生错误！");
        }finally {
            return comment;
        }
    }

    @Override
    public CommentInfo findOneById(int id) {
        CommentInfo commentInfo = null;
        try {
            commentInfo = commentMapper.getCommentById(id);
        }catch (Exception e){
            log.error("查询id="+id+"的评论失败!");
        }finally {
            return commentInfo;
        }
    }

    @Override
    public List<CommentInfo> findByContent(String content) {
        List<CommentInfo> comments = null;
        try {
            comments = commentMapper.getCommentByContent(content);
        }catch (Exception e){
            log.error("查询内容为'"+content+"'的评论失败!");
        }finally {
            return comments;
        }
    }

    @Override
    public List<CommentInfo> findByPostId(int postId) {
        List<CommentInfo> comments = null;
        try {
            comments = commentMapper.getCommentByPostId(postId);
        }catch (Exception e){
            log.error("查询话题id为"+postId+"的评论失败!");
        }finally {
            return comments;
        }
    }

    @Override
    public List<CommentInfo> findByUserId(int userId) {
        List<CommentInfo> comments = null;
        try {
            comments = commentMapper.getCommentByUserId(userId);
        }catch (Exception e){
            log.error("查询用户id为"+userId+"的评论失败!");
        }finally {
            return comments;
        }
    }

    @Override
    public List<CommentInfo> findByDocId(int docId) {
        List<CommentInfo> comments = null;
        try {
            comments = commentMapper.getCommentByDocId(docId);
        }catch (Exception e){
            log.error("查询文件id为"+docId+"的评论失败!");
        }finally {
            return comments;
        }
    }

    @Override
    @Transactional
    public int update(CommentInfo commentInfo) {
        int updateNum = 0;
        try {
            updateNum = commentMapper.updateComment(commentInfo);
        }catch (Exception e){
            log.error("Id ="+commentInfo.getCommentId()+"内容为:"+commentInfo.getCommentContent()+"的评论更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    @Transactional
    public int deleteById(int id) {
        int deleteNum = 0;
        try {
            deleteNum = commentMapper.deleteCommentById(id);
        }catch (Exception e){
            log.error("Id = "+id+"的评论删除失败!");
        }finally {
            return deleteNum;
        }
    }
}
