package com.ad.mapper;

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
 * 2020/8/23 17:40
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DocMapperTest {
    @Autowired
    private DocMapper docMapper;

    @Test
    void getDocById() {
        DocInfo result = docMapper.getDocById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void getDocByName() {
        List<DocInfo> result = docMapper.getDocByName("t");
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void getDocByType() {
        List<DocInfo> result = docMapper.getDocByType(DocTypeEnum.TXT.getCode());
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void getDocByUserId() {
        List<DocInfo> result = docMapper.getDocByUserId(6);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    void addDoc() {
        DocInfo doc1 = new DocInfo();
        doc1.setDocName("Test.txt");
        doc1.setDocPath("https://upload.piterator.com/158a5d8c8e4618f36c91ddefcdf3c995/raw");
        doc1.setDocSize(27);
        doc1.setDocType(DocTypeEnum.TXT.getCode());
        doc1.setDownloadNum(0);
        doc1.setStarNum(0);
        doc1.setUploadTime(new Date());
        doc1.setUserId(6);

        int result = docMapper.addDoc(doc1);
        Assert.assertNotEquals(0,result);


    }

    @Test
    void updateDoc() {
        DocInfo doc1 = new DocInfo();
        doc1.setDocName("Test.txt");
        doc1.setDocPath("https://upload.piterator.com/158a5d8c8e4618f36c91ddefcdf3c995/raw");
        doc1.setDocSize(28);
        doc1.setDocType(DocTypeEnum.TXT.getCode());
        doc1.setDownloadNum(0);
        doc1.setStarNum(0);
        doc1.setUploadTime(new Date());
        doc1.setUserId(6);
        doc1.setDocId(1);

        int result = docMapper.updateDoc(doc1);
        Assert.assertNotEquals(0,result);


    }

    @Test
    @Transactional
    void deleteDocById() {
        int result = docMapper.deleteDocById(1);
        Assert.assertNotEquals(0,result);
    }
}