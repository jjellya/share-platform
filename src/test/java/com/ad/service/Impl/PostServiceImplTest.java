package com.ad.service.Impl;

import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/18 21:12
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private PostServiceImpl postService;

    @Test
    void addPost() {
        PostInfo result = postService.addPost("TestTitle","TestContent",1);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        PostInfo result = postService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneByTag() {
        List<PostInfo> result = postService.findOneByTag("世界");
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findOneByTitle() {
        List<PostInfo> resultList = postService.findOneByTitle("跪求");
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    void findOneByContent() {
        List<PostInfo> resultList = postService.findOneByContent("tent");
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    void update() {
        PostInfo post= postService.findOneById(1);
        //post.setPostTitle("我有期末试卷");
        int result = postService.update(post);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void deleteById() {
        int result = postService.deleteById(2);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void findListOrderByTime(){
        List<PostDTO> result  = postService.findListOrderByTime(1,10);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result);
    }
}