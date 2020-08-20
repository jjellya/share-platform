package com.ad.mapper;

import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/20 18:32
 *
 * @version 1.0
 */
@Mapper
public interface TagLinkMapper {

    /**
     * 通过ID查找tagLink
     * @param id
     * @return
     */
    public TagLink getTagLinkById(Integer id);

    /**
     * 通过tagId查找tagLink
     * @param tagId
     * @return
     */
    public List<TagLink> getTagLinkByTagId(Integer tagId);


    /**
     * 通过postId查找tagLink
     * @param postId
     * @return
     */
    public List<TagLink> getTagLinkByPostId(Integer postId);

    /**
     * 通过docId查找tagLink
     * @param docId
     * @return
     */
    public List<TagLink> getTagLinkByDocId(Integer docId);


    /**
     * 添加TagLink
     * @param tagLink
     * @return
     */
    public int addTagLink(TagLink tagLink);


    /**
     * 更新tagLink
     * @param tagLink
     * @return
     */
    public  int updateTagLink(TagLink tagLink);

    /**
     * 删除指定id的tagLink列
     * @param id
     * @return
     */
    public  int deleteTagLinkById(Integer id);

}
