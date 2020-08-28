package com.ad.mapper;

import com.ad.pojo.TagInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 20:26
 *
 * @version 1.0
 */

@Mapper
public interface TagMapper {

    /**
     * 通过ID查找tag
     * @param id
     * @return
     */
    public TagInfo getTagById(Integer id);


    /**
     * 通过标题查找Tag
     * @param content
     * @return
     */
    public List<TagInfo> getTagByContent(String content);


    /**
     * 通过ID查找tag
     * @param postId
     * @return
     */
    public List<TagInfo> getTagByPostId(Integer postId);

    /**
     * 添加Tag
     * @param tag
     * @return
     */
    public int addTag(TagInfo tag);

    /**
     * 更新tag
     * @param tag
     * @return
     */
    public  int updateTag(TagInfo tag);


    /**
     * 删除指定id的tag列
     * @param id
     * @return
     */
    public  int deleteTagById(Integer id);
}
