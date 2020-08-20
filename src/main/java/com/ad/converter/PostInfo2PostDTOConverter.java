package com.ad.converter;

import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create By  @林俊杰
 * 2020/8/19 20:26
 *
 * @version 1.0
 */
@Slf4j
public class PostInfo2PostDTOConverter {

    public static PostDTO convert(PostInfo postInfo, UserInfo userInfo, List<TagInfo> tagInfoList){

            PostDTO postDTO = new PostDTO();
                    postDTO.setAvatarUrl(userInfo.getAvatarUrl());
                    postDTO.setUsername(userInfo.getUserName());
                    postDTO.setCommentNum(postInfo.getCommentNum());
                    postDTO.setContent(postInfo.getPostContent());
                    postDTO.setPostId(postInfo.getPostId());
                    List<String> tagContentList = new ArrayList<>();
                    for (TagInfo tag:tagInfoList
                         ) {
                            tagContentList.add(tag.getTagContent());
                    }
                    postDTO.setTag(tagContentList);
                    postDTO.setTitle(postInfo.getPostTitle());
                    postDTO.setUpdateTime(postInfo.getUpdateTime());

            return postDTO;
    }

}
