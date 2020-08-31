package com.ad.service.Impl;

import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.ad.pojo.*;
import com.ad.utils.ESconst;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.Doc;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/30 18:07
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SearchServiceImplTest {

    @Autowired
    private SearchServiceImpl searchService;


    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    void searchPage() throws IOException {
        List<Map<String, Object>> mapList = searchService.searchPage("Breeze", 0, 20);
        System.out.println(mapList.size());
        for (Map<String, Object> map : mapList) {
            System.out.println(map.toString());
        }
    }

    @Test
    void addDataList() {

    }

    @Test
    void addData() {
        PostInfo postInfo = postService.findOneById(20);
        UserInfo userInfo = userService.findOneById(postInfo.getUserId());
        DocInfo docInfo = docService.findOneById(2);
        Boolean result = searchService.addData(postInfo,userInfo,docInfo);
        Assert.assertNotEquals(false,result);
    }

    @Test
    void isExist() {
        Boolean result = searchService.isExist("bHLhPnQBrPh0kWai27SU");
        Assert.assertNotEquals(false,result);
    }

    @Test
    void getDataById() {
        ESDocument result = searchService.findOneById("cnIIP3QBrPh0kWaicLT_");
        System.out.println(result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    void updateDataById() {
        ESDocument esDocument = searchService.findOneById("cnIIP3QBrPh0kWaicLT_");
        esDocument.setUpdateTime(new Date());
        esDocument.setTag3("考试");
        Boolean result = searchService.updateDataById(esDocument, "cnIIP3QBrPh0kWaicLT_");
        Assert.assertNotEquals(false,result);

    }

    @Test
    void deleteDataById() {
        Boolean result = searchService.deleteDataById("dHIvP3QBrPh0kWaiTrSK");
        Assert.assertNotEquals(false,result);
    }

    @Test
    void searchPost() {
        List<PostDTO> result = searchService.searchPost("大一", 1, 5);
        System.out.println(result);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void searchDoc() {
        List<DocShowDTO> result = searchService.searchDoc("test", 1, 10);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }
}