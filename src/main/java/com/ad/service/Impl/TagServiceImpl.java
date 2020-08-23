package com.ad.service.Impl;

import com.ad.mapper.TagMapper;
import com.ad.pojo.TagInfo;
import com.ad.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 22:01
 *
 * @version 1.0
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    @Transactional
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

    @Override
    public TagInfo findOneById(Integer id) {
        TagInfo tag = null;
        tag = tagMapper.getTagById(id);
        return tag;
    }

    @Override
    public List<TagInfo> findByContent(String content) {
        List<TagInfo> tagList = null;
        tagList = tagMapper.getTagByContent(content);
        return tagList;
    }

    @Override
    @Transactional
    public int update(TagInfo tag) {
        int updateNum = 0;
        try {
            updateNum = tagMapper.updateTag(tag);
        }catch (Exception e){
            log.error("tagId ="+tag.getTagId()+"内容为:"+tag.getTagContent()+"的标签更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    public int deleteById(Integer id) {
        int deleteNum = 0;
        try {
            deleteNum = tagMapper.deleteTagById(id);
        }catch (Exception e){
            log.error("tagId = "+id+"的标签删除失败!");
        }finally {
            return deleteNum;
        }
    }
}
