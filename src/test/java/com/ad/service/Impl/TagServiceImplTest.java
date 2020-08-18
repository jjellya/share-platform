package com.ad.service.Impl;

import com.ad.pojo.TagInfo;
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
 * 2020/8/18 22:14
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TagServiceImplTest {

    @Autowired
    private TagServiceImpl tagService;

    @Test
    void addTag() {
        TagInfo result =tagService.addTag("易班拯救学生");
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        TagInfo result = tagService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneByContent() {
        List<TagInfo> resultList = tagService.findOneByContent("易班");
        Assert.assertNotEquals(0,resultList.size());
    }

    @Test
    void update() {
        TagInfo tag = tagService.findOneById(1);
        tag.setTagContent("易班拯救世界");
        int result = tagService.update(tag);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void deleteById() {
      Assert.assertNotEquals(0,tagService.deleteById(2));
    }
}