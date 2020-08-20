package com.ad.mapper;

import com.ad.pojo.TagLink;
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
 * 2020/8/20 19:58
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TagLinkMapperTest {

    @Autowired
    private TagLinkMapper tagLinkMapper;

    @Test
    void getTagLinkById() {
        TagLink result = tagLinkMapper.getTagLinkById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void getTagLinkByTagId() {
        List<TagLink> result = tagLinkMapper.getTagLinkByTagId(1);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    @Transactional
    void addTagLinkToDoc() {
        TagLink tagLink = new TagLink();
        tagLink.setDocId(2);
        tagLink.setTagId(1);
        int result  = tagLinkMapper.addTagLink(tagLink);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void getTagLinkByPostId() {
        List<TagLink> result = tagLinkMapper.getTagLinkByPostId(1);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void getTagLinkByDocId() {
        List<TagLink> result = tagLinkMapper.getTagLinkByDocId(1);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void updateTagLink() {
        TagLink tagLink = new TagLink();
        tagLink.setTlinkId(1);
        tagLink.setDocId(null);
        tagLink.setTagId(1);
        tagLink.setPostId(1);
        int result  = tagLinkMapper.updateTagLink(tagLink);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void deleteTagLinkById() {
        int result  = tagLinkMapper.deleteTagLinkById(2);
        Assert.assertNotEquals(0,result);
    }
}