package com.ad.service.Impl;

import com.ad.pojo.TagLink;
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
 * 2020/8/20 21:01
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TagLinkServiceImplTest {

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Test
    void addTagLinkToPost() {
        TagLink result = tagLinkService.addTagLinkToPost(2,1);
        Assert.assertNotNull(result);
    }

    @Test
    void addTagLinkToDoc() {
        TagLink result = tagLinkService.addTagLinkToDoc(1,1);
        Assert.assertNotNull(result);
    }

    @Test
    void findByPostId() {
        List<TagLink> result = tagLinkService.findByPostId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByTagId() {
        List<TagLink> result = tagLinkService.findByTagId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByDocId() {
        List<TagLink> result = tagLinkService.findByDocId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void update() {
        TagLink tagLink = new TagLink();
        tagLink.setTlinkId(10);
        tagLink.setTagId(3);
        tagLink.setPostId(1);
        int result  = tagLinkService.update(tagLink);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void deleteById() {
        int result  = tagLinkService.deleteById(11);
        Assert.assertNotEquals(0,result);
    }
}