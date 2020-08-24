package com.ad.mapper;


import com.ad.pojo.PostInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/17 20:10
 *
 * @version 1.0
 */

@Mapper
public interface PostMapper {

    /**
     * 通过ID查找post
     * @param id
     * @return
     */
    public PostInfo getPostById(Integer id);


    /**
     * 通过标题查找post
     * @param title
     * @return
     */
    public List<PostInfo> getPostByTitle(String title);

    /**
     * 通过内容查找post
     * @param content
     * @return
     */
    public List<PostInfo> getPostByContent(String content);

    /**
     * 通过标签查找post
     * @param tagContent
     * @return
     */
    public List<PostInfo> getPostByTag(String tagContent);

    /**
     * 添加post
     * @param post
     * @return
     */
    public int addPost(PostInfo post);


    /**
     * 更新post
     * @param post
     * @return
     */
    public  int updatePost(PostInfo post);


    /**
     * 删除指定id的post列
     * @param id
     * @return
     */
    public  int deletePostById(Integer id);

    /**
     * 时间倒排展示post
     * @param offset
     * @param size
     * @return
     */
    public List<PostInfo> getPageOrderByTime(Integer offset,Integer size);

    /**
     * 时间倒排展示post
     * @param offset
     * @param size
     * @param content
     * @return
     */
    public List<PostInfo> getPageOrderByTimeAndGrade(Integer offset,Integer size,String content);

}
