package com.ad.service.Impl;

import com.ad.mapper.DocMapper;
import com.ad.mapper.LinkMapper;
import com.ad.pojo.DocInfo;
import com.ad.pojo.LinkInfo;
import com.ad.pojo.PostInfo;
import com.ad.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 18:14
 *
 * @version 1.0
 */
@Service
@Slf4j
public class LinkServiceImpl implements LinkService {

    @Autowired
    private DocMapper docMapper;

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Transactional
    public LinkInfo addStar(Integer userId, Integer docId) {

        if (linkMapper.getLinkByUserIdAndDocId(userId,docId)==null){
            LinkInfo link1 = new LinkInfo();
            link1.setDocId(docId);
            link1.setUserId(userId);

            try {
                linkMapper.addLink(link1);
                DocInfo doc1 = docMapper.getDocById(docId);
                doc1.setStarNum(doc1.getStarNum()+1);
                docMapper.updateDoc(doc1);
            }catch (Exception e){
                log.error("用户ID = "+userId+"收藏资源docId = "+docId+"发生错误!");
            }finally {
                return link1;
            }
        }else {
            log.error("用户ID= "+userId+"已收藏资源docId= "+docId+",请勿重复添加！");
            return null;
        }
    }

    @Override
    public LinkInfo findOneById(Integer id) {
        LinkInfo linkInfo = null;
        try {
            linkInfo = linkMapper.getLinkById(id);
        }catch (Exception e){
            log.error("查询id="+id+"的评论失败!");
        }finally {
            return linkInfo;
        }
    }

    @Override
    public List<LinkInfo> findByDocId(Integer docId) {
        List<LinkInfo> links = null;
        try {
            links = linkMapper.getLinkByDocId(docId);
        }catch (Exception e){
            log.error("查询docId="+docId+"的收藏关系失败!");
        }finally {
            return links;
        }
    }

    @Override
    public List<LinkInfo> findByUserId(Integer userId) {
        List<LinkInfo> links = null;
        try {
            links = linkMapper.getLinkByUserId(userId);
        }catch (Exception e){
            log.error("查询用户Id="+userId+"的收藏star关系失败!");
        }finally {
            return links;
        }
    }

    @Override
    @Transactional
    public int update(LinkInfo linkInfo) {
        int updateNum = 0;
        try {
            updateNum = linkMapper.updateLink(linkInfo);
        }catch (Exception e){
            log.error("资源Id ="+linkInfo.getDocId()+"用户Id为:"+linkInfo.getUserId()+"的收藏关系star更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    @Transactional
    public int deleteById(Integer id) {
        int deleteNum = 0;
        try {
            deleteNum = linkMapper.deleteLinkById(id);
        }catch (Exception e){
            log.error("Id= "+id+"的收藏关系star取消失败!");
        }finally {
            return deleteNum;
        }
    }

    @Override
    public List<DocInfo> findDocListByUserId(Integer userId) {
        List<DocInfo> docs = new ArrayList<>();
        try{
            List<LinkInfo> links = findByUserId(userId);
            for (LinkInfo link:links
                 ) {
                docs.add(docMapper.getDocById(link.getDocId()));
            }
        }catch (Exception e){
            log.error("获取用户ID= "+userId+" 收藏资源列表失败");
            docs.clear();
        }
        return docs;
    }

    @Override
    @Transactional
    public int cancelStar(Integer userId, Integer docId) {
        int result = 0;
        try {
            LinkInfo linkInfo  = linkMapper.getLinkByUserIdAndDocId(userId,docId);
            result = linkMapper.deleteLinkById(linkInfo.getLinkId());
            DocInfo doc1 = docMapper.getDocById(docId);
            doc1.setStarNum(doc1.getStarNum()-1);
            docMapper.updateDoc(doc1);
        }catch (Exception e){
            log.error("取消Star关系失败!,docId= "+docId+"; userId= "+userId);
        }
        return result;
    }
}
