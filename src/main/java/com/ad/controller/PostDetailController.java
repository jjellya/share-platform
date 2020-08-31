package com.ad.controller;

import com.ad.VO.PostDetailsVO;
import com.ad.VO.ResultVO;
import com.ad.converter.CommentInfo2CommentDTOConverter;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.CommentDTO;
import com.ad.dto.PostDTO;
import com.ad.pojo.*;
import com.ad.service.Impl.*;
import com.ad.utils.MyDateUtil;
import com.ad.utils.ResultVOUtil;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping(value = "/api/postdetails",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO details(@RequestParam(value = "postId",required = false)int postId){
        //创建帖子信息
        PostInfo postInfo = postService.findOneById(postId);
        PostDTO postDTO = new PostDTO();
        //贴主信息
        UserInfo userInfo = userService.findOneById(postInfo.getUserId());

        //通过postId获取tagLink
        List<TagLink>tagLinkList = tagLinkService.findByPostId(postId);
        List<TagInfo>tagInfoList = new ArrayList<>();
        if (tagLinkList.size()>=1)
        for (int i=0;i<tagLinkList.size();i++){
            tagInfoList.add(tagService.findOneById(tagLinkList.get(i).getTagId()));
            System.out.println(tagInfoList.get(i).getTagId()+" : " +tagInfoList.get(i).getTagContent());
        }


        postDTO= PostInfo2PostDTOConverter.convert(postInfo,userInfo,tagInfoList);

        List<CommentInfo>commentInfoList = commentService.findByPostId(postId);
        List<CommentDTO>commentDTOList = new ArrayList<>();
        CommentDTO commentDTO = null;
        UserInfo userInfo1 = null;
        for (int i=0;i<commentInfoList.size();i++){
            userInfo1 = userService.findOneById(commentInfoList.get(i).getUserId());
            commentDTO = CommentInfo2CommentDTOConverter.convert(userInfo1,commentInfoList.get(i));
            commentDTOList.add(commentDTO);
        }
        PostDetailsVO postDetailsVO = new PostDetailsVO();
        postDetailsVO.setPostDTO(postDTO);
        postDetailsVO.setCommentDTOList(commentDTOList);

        return ResultVOUtil.build(200,"success",postDetailsVO);
    }
}
