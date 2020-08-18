package com.ad.service.Impl;

import com.ad.pojo.UserInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/18 10:42
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl service;

    @Test
    void findOneByOpenId() {
        UserInfo result = service.findOneByOpenId("18102948");
        Assert.assertNotNull(result);
    }
}