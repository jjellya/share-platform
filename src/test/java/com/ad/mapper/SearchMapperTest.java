package com.ad.mapper;

import com.ad.pojo.DocInfo;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagLink;
import com.ad.service.Impl.TagLinkServiceImpl;
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
 * 2020/8/30 21:08
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SearchMapperTest {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private TagLinkServiceImpl tagLinkService;



    @Test
    void queryPostInfo() {
        List<PostInfo> result = searchMapper.queryPostInfo("大一",1,10);
        System.out.println(result);
        System.out.println(result.size());
        for (PostInfo postInfo : result) {
            System.out.println(postInfo.getPostId()+" "+postInfo.getPostTitle());
        }
        Assert.assertNotEquals(0,result.size());

        List<TagLink> links = tagLinkService.findByTagId(19);
        for (TagLink link:links
             ) {
            System.out.println(link.getPostId());
        }
    }

    @Test
    void queryDocInfo() {
        List<DocInfo> result = searchMapper.queryDocInfo("大一",1,10);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }
}