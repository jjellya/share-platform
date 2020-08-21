package com.ad.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/21 21:29
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RecommendServiceImplTest {
    @Autowired
    private RecommendServiceImpl recommendService;

    @Test
    void findListOrderByRecommend() {
        recommendService.findListOrderByRecommend(1,10,6);
    }
}