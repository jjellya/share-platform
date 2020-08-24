package com.ad.converter;

import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.utils.MyDateUtil;
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
                    String content ;String img;
                    if (postInfo.getPostContent().contains("+")){
                         content = postInfo.getPostContent().split("\\+")[0];
                         img = postInfo.getPostContent().split("\\+")[1];
                    }else if (postInfo.getPostContent().contains("http")){
                        content = null;
                        img = postInfo.getPostContent();
                    }else {
                        content = postInfo.getPostContent();
                        img = null;
                    }
                    postDTO.setContent(content);
                    postDTO.setImg(img);
                    postDTO.setPostId(postInfo.getPostId());
                    List<String> tagContentList = new ArrayList<>();
                    for (TagInfo tag:tagInfoList
                         ) {
                            tagContentList.add(tag.getTagContent());
                    }
                    postDTO.setTag(tagContentList);
                    postDTO.setTitle(postInfo.getPostTitle());
        System.out.println(postInfo.getUpdateTime().getTime());
                    postDTO.setUpdateTime(MyDateUtil.convertTimeToFormat(postInfo.getUpdateTime().getTime()));

            return postDTO;
    }

}
