package com.ad.service.Impl;

import com.ad.enums.DocTypeEnum;
import com.ad.pojo.DocInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/23 20:25
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DocServiceImplTest {

    @Autowired
    private DocServiceImpl docService;

    @Test
    void addDoc() {
        DocInfo result = docService.addDoc("test2.txt", DocTypeEnum.TXT.getCode(),27,"https://upload.piterator.com/8478f92261b96523f0c6c83c95891eba/raw",6);
        Assert.assertNotNull(result);
    }

    @Test
    void findOneById() {
        DocInfo result = docService.findOneById(2);
        Assert.assertNotNull(result);
    }

    @Test
    void findByName() {
        List<DocInfo> result = docService.findByName("t");
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByType() {
        List<DocInfo> result = docService.findByType(DocTypeEnum.TXT.getCode());
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void findByUserId() {
        List<DocInfo> result = docService.findByUserId(6);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void update() {
        DocInfo doc1 = new DocInfo();
        doc1.setDocName("Test.txt");
        doc1.setDocPath("https://upload.piterator.com/158a5d8c8e4618f36c91ddefcdf3c995/raw");
        doc1.setDocSize(27);
        doc1.setDocType(DocTypeEnum.TXT.getCode());
        doc1.setDownloadNum(0);
        doc1.setStarNum(0);
        doc1.setUploadTime(new Date());
        doc1.setUserId(6);
        doc1.setDocId(1);

        int result  = docService.update(doc1);
        Assert.assertNotEquals(0,result);
    }

    @Test
    @Transactional
    void deleteById() {
        int result  = docService.deleteById(1);
        Assert.assertNotEquals(0,result);
    }
}