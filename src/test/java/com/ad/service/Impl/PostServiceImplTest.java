package com.ad.service.Impl;

import com.ad.dto.PostDTO;
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
 * 2020/8/26 11:49
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class PostServiceImplTest {
    @Autowired
    private PostServiceImpl postService;

    @Test
    void findListOrderByAuthor() {
        List<PostDTO> result = postService.findListOrderByAuthor(1,10,6);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void countPost() {
        int result = postService.countPost();
        System.out.println(result);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void countMyPost() {
        int result = postService.countMyPost(6);
        System.out.println(result);
        Assert.assertNotEquals(0,result);
    }
}