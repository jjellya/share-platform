package com.ad.service.Impl;

import com.ad.pojo.UserScore;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/26 13:02
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RecommendServiceImplTest {
    @Autowired
    private RecommendServiceImpl recommendService;

    @Test
    void addScore() {
        UserScore result = recommendService.addScore(3,0,18,0);
        Assert.assertNotNull(result);
    }


    @Test
    void clickAgain() {
        int result = recommendService.clickAgain(31);
        Assert.assertNotEquals(0,result);
    }


    @Test
    void addComment() {
        int result = recommendService.addComment(31);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void addStar() {
        int result = recommendService.addStar(31);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void findOneByUserIdAndPostId() {
        UserScore result = recommendService.findOneByUserIdAndPostId(3,18);
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}