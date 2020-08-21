package com.ad.service.Impl;

import com.ad.mapper.TagMapper;
import com.ad.pojo.TagInfo;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class TagServiceImplTest {

    @Autowired
    private TagServiceImpl tagService;
    private TagMapper tagMapper;

    @Test
    public TagInfo addTag(String content) {
        TagInfo tag = new TagInfo();
        tag.setTagContent(content);

        if(tagMapper.addTag(tag)!=0){
            log.info("已成功创建内容为："+tag.getTagContent()+" 的标签(tagId = "+tag.getTagId()+")");
        }else {
            log.error("\""+content+"\""+"标签创建失败");
        }
        return tag;
    }

    @Test
    void findOneById() {
        TagInfo result = tagService.findOneById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void findByContent() {
        List<TagInfo> resultList = tagService.findByContent("易班");
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