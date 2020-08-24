package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.MyDateUtil;
import com.ad.utils.ResultVOUtil;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-23 19:42
 */
@Controller
@Slf4j
public class PostDetailController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private TagServiceImpl tagService;

    @RequestMapping(value = "/api/postdetails",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO details(@RequestParam(value = "postId",required = false)int postId){
        //创建帖子信息
        PostInfo postInfo = postService.findOneById(postId);
        PostDTO postDTO = new PostDTO();
        //贴主信息
        UserInfo userInfo = userService.findOneById(postInfo.getUserId());
        //通过帖子Id 获得 TagLink
        List<TagLink>tagLinks = tagLinkService.findByPostId(postId);
        List<String>tag = new ArrayList<>();
        TagInfo tagInfo;
        for(int i=0;i<tagLinks.size();i++){
            //通过taglink 获得 tagid，从而获得taginfo
            tagInfo = tagService.findOneById(tagLinks.get(i).getTagId());
            tag.add(tagInfo.getTagContent());
        }

        postDTO.setUsername(userInfo.getUserName());
        postDTO.setUpdateTime(MyDateUtil.convertTimeToFormat(postInfo.getUpdateTime().getTime()));
        postDTO.setTag(tag);
        postDTO.setTitle(postInfo.getPostTitle());
        postDTO.setPostId(postId);
        postDTO.setContent(postInfo.getPostContent());
        postDTO.setCommentNum(postInfo.getCommentNum());
        postDTO.setAvatarUrl(userInfo.getAvatarUrl());

        return ResultVOUtil.build(200,"success",postDTO);
    }
}
