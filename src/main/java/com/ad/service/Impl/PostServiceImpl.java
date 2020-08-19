package com.ad.service.Impl;

import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.mapper.PostMapper;
import com.ad.mapper.TagMapper;
import com.ad.mapper.UserMapper;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 20:47
 *
 * @version 1.0
 */
@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public PostInfo addPost(String postTitle, String postContent, Integer userId) {
        PostInfo post = new PostInfo();

        post.setPostTitle(postTitle);
        post.setPostContent(postContent);
        post.setUserId(userId);

        post.setCreateTime(new Date());
        post.setUpdateTime(post.getCreateTime());

        postMapper.addPost(post);
        log.info("已成功创建新帖子: Id="+post.getPostId()+",title = "+post.getPostTitle()+",userId = "+post.getUserId());
        return post;
    }

    @Override
    public PostInfo findOneById(Integer id) {
        PostInfo postInfo = null;
        postInfo = postMapper.getPostById(id);
        return postInfo;
    }

    @Override
    public List<PostInfo> findOneByTag(String tagContent) {
        List<PostInfo> postList = null;
        postList = postMapper.getPostByTag(tagContent);
        return postList;
    }

    @Override
    public List<PostInfo> findOneByTitle(String title) {
        List<PostInfo> postList = null;
        postList = postMapper.getPostByTitle(title);
        return postList;
    }

    @Override
    public List<PostInfo> findOneByContent(String content) {
        List<PostInfo> postList = null;
        postList = postMapper.getPostByContent(content);
        return postList;
    }

    @Override
    public int update(PostInfo post) {
        int updateNum = 0;
        try {
            post.setUpdateTime(new Date());
            updateNum = postMapper.updatePost(post);
        }catch (Exception e){
            log.error("用户: Id="+post.getUserId()+"的"+"postId = "+post.getPostId()+"的帖子更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    public int deleteById(Integer id) {
        int deleteNum = 0;
        try {
            deleteNum = postMapper.deletePostById(id);
        }catch (Exception e){
            log.error("postId = "+id+"的帖子删除失败!");
        }finally {
            return deleteNum;
        }
    }

    @Override
    public List<PostDTO> findListOrderByTime(int offset, int size) {

        List<PostDTO> postDTOList = null;

        if(size == 0){
            log.error("请求错误,size = 0");
            return postDTOList;
        }

        UserInfo tempUser;
        TagInfo tempTag;
        //TODO 可尝试优化数据库,利用数据库冗余字段通过list.stream().map(e->convert(e)).collect(Collectors.toList());来加快填充速度
        List<PostInfo> postList = postMapper.getPageOrderByTime(offset,size);
        if(postList.size()>0) {
            for (PostInfo post : postList
            ) {
                tempUser = userMapper.getUserById(post.getUserId());
                tempTag = tagMapper.getTagByPostId(post.getPostId());
                postDTOList.add(PostInfo2PostDTOConverter.convert(post,tempUser,tempTag));
            }
        }

        return postDTOList;
    }
}
