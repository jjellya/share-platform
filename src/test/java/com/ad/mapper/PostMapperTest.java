package com.ad.mapper;

import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/17 21:50
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class PostMapperTest {
    @Autowired
    private PostMapper postMapper;


    @Test
    void getPostById() {
        PostInfo result = postMapper.getPostById(0);
        Assert.assertNull(result);

    }

    @Test
    void getPostByName() {
        List<PostInfo> result = postMapper.getPostByTitle("空字符串");
        System.out.println(result.toString());

        Assert.assertEquals(0,result.size());
    }

    @Test
    void getPostByTag() {
        List<PostInfo> resultList = postMapper.getPostByTag("易班");
        System.out.println(resultList.toString());
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    void addPost(){
        PostInfo post = new PostInfo();
        post.setCreateTime(new Date());
        post.setUpdateTime(post.getCreateTime());
        post.setPostContent("https://wx2.sbimg.cn/2020/08/17/3jUpM.jpg");
        post.setPostTag1(null);
        post.setPostTag2(null);
        post.setPostTitle("跪求期末试卷!");
        post.setUserId(2);
        int result = postMapper.addPost(post);

        Assert.assertNotEquals(0,result);

    }

    @Test
    void getPageOrderByTime(){
        int offset  = 1;
        int size = 10;
        List<PostInfo> result = postMapper.getPageOrderByTime(offset,size);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }
}