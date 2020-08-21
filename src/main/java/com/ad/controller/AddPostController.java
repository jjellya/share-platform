package com.ad.controller;


import com.ad.VO.ResultVO;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class AddPostController {

    //创建帖子

    @Autowired
    PostServiceImpl postService;

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    TagLinkServiceImpl tagLinkService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "/api/addpost",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO addPost(@RequestParam(value = "postTitle",required = false)String postTitle,
                            @RequestParam(value = "postContent",required = false)String postContent,
                            @RequestParam(value = "userId",required = false)int userId,
                            @RequestParam(value = "postTag1",required = false)String postTag1,
                            @RequestParam(value = "postTag2",required = false)String postTag2){
        //创建帖子
        PostInfo postInfo = postService.addPost(postTitle,postContent,userId);

        //创建标签
        TagInfo tagInfo1 = tagService.addTag(postTag1);
        TagInfo tagInfo2 = tagService.addTag(postTag2);

        //创建标签与帖子的链接
        TagLink tagLink1 = tagLinkService.addTagLinkToPost(tagInfo1.getTagId(),postInfo.getPostId());
        TagLink tagLink2 = tagLinkService.addTagLinkToPost(tagInfo2.getTagId(),postInfo.getPostId());

        //修改用户帖子信息
        UserInfo userInfo = userService.findOneById(userId);
        userInfo.setPostNum(userInfo.getPostNum()+1);
        userService.update(userInfo);
        log.info("用户: Id="+userInfo.getUserId()+",name = "+userInfo.getUserName()+"于 "+new Date()+
                " 帖子数加一，帖子数量变更为 "+ userInfo.getPostNum());
        System.out.println(userInfo.getPostNum());

        return ResultVOUtil.build(200,"success",postInfo);
    }
}
