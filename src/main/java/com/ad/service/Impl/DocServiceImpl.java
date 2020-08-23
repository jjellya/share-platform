package com.ad.service.Impl;

import com.ad.mapper.DocMapper;
import com.ad.pojo.DocInfo;
import com.ad.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/23 19:43
 *
 * @version 1.0
 */
@Slf4j
@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocMapper docMapper;

    @Override
    @Transactional
    public DocInfo addDoc(String name, Integer type, Integer size, String path, Integer userId) {
        DocInfo docInfo = new DocInfo();
        docInfo.setUserId(userId);
        docInfo.setUploadTime(new Date());
        docInfo.setStarNum(0);
        docInfo.setDownloadNum(0);
        docInfo.setDocType(type);
        docInfo.setDocSize(size);
        docInfo.setDocPath(path);
        docInfo.setDocName(name);

        if(docMapper.addDoc(docInfo)!=0){
            log.info("已成功创建新文件资源: Id="+docInfo.getDocId()+",Name = "+docInfo.getDocName()+",userId = "+docInfo.getUserId());
        }else {
            log.error("创建文件资源失败，Id = "+docInfo.getDocId());
        }
        return docInfo;
    }

    @Override
    public DocInfo findOneById(Integer id) {
        DocInfo doc =  null;
        doc = docMapper.getDocById(id);
        return doc;
    }

    @Override
    public List<DocInfo> findByName(String name) {
        List<DocInfo> docs = null;
        docs = docMapper.getDocByName(name);
        return docs;
    }

    @Override
    public List<DocInfo> findByType(Integer type) {
        List<DocInfo> docs = null;
        docs = docMapper.getDocByType(type);
        return docs;
    }

    @Override
    public List<DocInfo> findByUserId(Integer userId) {
        List<DocInfo> docs = null;
        docs = docMapper.getDocByUserId(userId);
        return docs;
    }

    @Override
    @Transactional
    public int update(DocInfo doc) {
        int updateNum = 0;
        try {
            updateNum = docMapper.updateDoc(doc);
        }catch (Exception e){
            log.error("docId ="+doc.getDocId()+"文件名为:"+doc.getDocName()+"的文件信息更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    @Transactional
    public int deleteById(Integer id) {
        int deleteNum = 0;
        try {
            deleteNum = docMapper.deleteDocById(id);
        }catch (Exception e){
            log.error("docId ="+id+"的文件信息删除失败!");
        }finally {
            return deleteNum;
        }
    }
}
