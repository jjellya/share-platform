package com.ad.service.Impl;

import com.ad.enums.CommentTypeEnum;
import com.ad.pojo.CommentInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/28 13:24
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    void addComment() {
        CommentInfo result = commentService.addComment(1,6,1, 1,"谢邀以下是我分享的文件!");
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        CommentInfo result = commentService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findByContent() {
        List<CommentInfo> result = commentService.findByContent("顶");
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByPostId() {
        List<CommentInfo> result = commentService.findByPostId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByUserId() {
        List<CommentInfo> result = commentService.findByUserId(6);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByDocId() {
        List<CommentInfo> result = commentService.findByDocId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void update() {
        CommentInfo comment = new CommentInfo();
        comment.setUserId(16);
        comment.setPostId(1);
        comment.setCommentContent("难顶");
        comment.setCommentType(CommentTypeEnum.PLAIN_TEXT.getCode());
        comment.setCommentTime(new Date());
        comment.setCommentId(2);

        int result = commentService.update(comment);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void deleteById() {
        int result = commentService.deleteById(6);
        Assert.assertNotEquals(0,result);
    }
}