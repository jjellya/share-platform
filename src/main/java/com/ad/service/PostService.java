package com.ad.service;



import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 20:41
 *
 * @version 1.0
 */
public interface PostService {

    PostInfo addPost(String postTitle, String postContent, Integer userId);

    PostInfo findOneById(Integer id);

    List<PostInfo> findOneByTag(String tagContent);

    List<PostInfo> findOneByTitle(String title);

    List<PostInfo> findOneByContent(String content);

    int update(PostInfo post);

    int deleteById(Integer id);

    List<PostDTO> findListOrderByTime(int offset,int size);

    List<PostDTO> findListOrderByTimeAndGrade(int offset,int size,int grade);

    List<PostDTO> findListOrderByGradeWithOutDoc(int offset,int size,int grade);

    List<PostDTO> findListOrderByAuthor(int offset,int size,int userId);

    int countPost();

    int countMyPost(int userId);
}
