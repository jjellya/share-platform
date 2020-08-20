package com.ad.service.Impl;

import com.ad.mapper.TagLinkMapper;
import com.ad.pojo.TagLink;
import com.ad.service.TagLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/20 20:51
 *
 * @version 1.0
 */
@Service
@Slf4j
public class TagLinkServiceImpl implements TagLinkService {

    @Autowired
    private TagLinkMapper tagLinkMapper;

    @Override
    public TagLink addTagLinkToPost(Integer tagId, Integer postId) {
        TagLink tagLink = new TagLink();
        tagLink.setTagId(tagId);
        tagLink.setPostId(postId);
        try {
            tagLinkMapper.addTagLink(tagLink);
            log.info("添加tagId = "+tagId+"的标签到postId = "+postId+"的帖子成功");
        }catch (Exception e){
            log.error("添加tagId = "+tagId+"的标签到postId = "+postId+"的帖子失败");
        }
        return tagLink;
    }

    @Override
    public TagLink addTagLinkToDoc(Integer tagId, Integer docId) {
        TagLink tagLink = new TagLink();
        tagLink.setTagId(tagId);
        tagLink.setDocId(docId);
        try {
            tagLinkMapper.addTagLink(tagLink);
            log.info("添加tagId = "+tagId+"的标签到postId = "+docId+"的文件资源成功");
        }catch (Exception e){
            log.error("添加tagId = "+tagId+"的标签到postId = "+docId+"的文件资源失败");
        }
        return tagLink;
    }

    @Override
    public List<TagLink> findByPostId(Integer postId) {
        return tagLinkMapper.getTagLinkByPostId(postId);
    }

    @Override
    public List<TagLink> findByTagId(Integer tagId) {
        return tagLinkMapper.getTagLinkByTagId(tagId);
    }

    @Override
    public List<TagLink> findByDocId(Integer docId) {
        return tagLinkMapper.getTagLinkByDocId(docId);
    }

    @Override
    public int update(TagLink tagLink) {
        int updateNum = 0;
        try {
            updateNum = tagLinkMapper.updateTagLink(tagLink);
            log.info("tlinkId = "+tagLink.getTlinkId()+"的标签关系修改成功");
        }catch (Exception e){
            log.error("tlinkId = "+tagLink.getTlinkId()+"的标签关系修改失败");
        }finally {
            return updateNum;
        }
    }

    @Override
    public int deleteById(Integer id) {
        int deleteNum = 0;
        try {
            deleteNum = tagLinkMapper.deleteTagLinkById(id);
            log.info("tlinkId = "+id+"的标签关系修改成功");
        }catch (Exception e){
            log.error("tlinkId = "+id+"的标签关系修改失败");
        }finally {
            return deleteNum;
        }
    }
}
