package com.ad.service.Impl;

import com.ad.mapper.LinkMapper;
import com.ad.pojo.DocInfo;
import com.ad.pojo.LinkInfo;
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
 * 2020/8/28 18:24
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class LinkServiceImplTest {

    @Autowired
    private LinkServiceImpl linkService;

    @Test
    void addStar() {
        LinkInfo result = linkService.addStar(8,1);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        LinkInfo result = linkService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findByDocId() {
        List<LinkInfo> result  = linkService.findByDocId(1);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());

    }

    @Test
    void findByUserId() {
        List<LinkInfo> result  = linkService.findByUserId(16);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void update() {
        LinkInfo linkInfo = linkService.findOneById(4);
        linkInfo.setUserId(16);
        int result = linkService.update(linkInfo);
        Assert.assertNotEquals(0,result);

    }

    @Test
    void cancelStarById() {
        int result = linkService.deleteById(4);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void findDocListByUserId() {
        List<DocInfo> result  = linkService.findDocListByUserId(16);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void cancelStar() {
      int result = linkService.cancelStar(8,1);
      Assert.assertEquals(0,result);
    }
}