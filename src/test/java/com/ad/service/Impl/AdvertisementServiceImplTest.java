package com.ad.service.Impl;

import com.ad.pojo.AdInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/31 9:45
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AdvertisementServiceImplTest {

    @Autowired
    private AdvertisementServiceImpl advertisementService;

    @Test
    void addAd() {
        AdInfo result = advertisementService.addAd(6, "AD镁铝硅磷招收前端研发啦!", "https://wx2.sbimg.cn/2020/08/31/6Hk5w.png", "+QQ 3596405356,+86 19927539729");
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        AdInfo result = advertisementService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findByTitle() {
        List<AdInfo> result = advertisementService.findByTitle("招人");
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByUserId() {
        List<AdInfo> result = advertisementService.findByUserId(6);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findContactByUserId() {
        List<String> result = advertisementService.findContactByUserId(6);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByContact() {
        List<AdInfo> result = advertisementService.findByContact("199");
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void update() {
        AdInfo one = advertisementService.findOneById(2);
        one.setAdContact("+QQ 3596405356");
        int result = advertisementService.update(one);
        Assert.assertNotEquals(0,result);
    }

    @Test
    @Transactional
    void deleteById() {
        int result = advertisementService.deleteById(2);
        Assert.assertNotEquals(0,result);
    }
}