package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.converter.CommentInfo2CommentDTOConverter;
import com.ad.dto.CommentDTO;
import com.ad.pojo.CommentInfo;
import com.ad.pojo.PostInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.CommentServiceImpl;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.RecommendServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WenZhikun
 * @data 2020-08-28 16:10
 */
@Controller
@Slf4j
public class CommentController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RecommendServiceImpl recommendService;

    @RequestMapping("/api/comment")
    @ResponseBody
    @Transactional
    public ResultVO comment(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                            @RequestParam(value = "postId",required = false,defaultValue = "1")int postId,
                            @RequestParam(value = "content",required = false)String content){
        if(content.length()>255){
            return ResultVOUtil.build(501,"error","评论字数过长");
        }else if(content.length()==0){
            return ResultVOUtil.build(501,"error","评论内容不能为空");
        }
        //创建评论
        //没有文件关联，因此设置为0，类型为无附带文件因此为0
        CommentInfo commentInfo = commentService.addComment(postId,userId,0,0,content);
        System.out.println("测试数据------------------------>"+commentInfo);
        UserInfo userInfo = userService.findOneById(userId);
        CommentDTO commentDTO = CommentInfo2CommentDTOConverter.convert(userInfo,commentInfo);
        System.out.println("测试数据------------------------->"+commentDTO);

        //评论数量改变
        PostInfo postInfo = postService.findOneById(postId);
        System.out.println(postInfo.getCommentNum());
        postInfo.setCommentNum(postInfo.getCommentNum()+1);
        System.out.println(postInfo.getCommentNum());
        int i = postService.update(postInfo);
        System.out.println(i);
        Map<String,Object>map = new HashMap<>();
        map.put("commentDTO",commentDTO);
        //评论则对该话题评分+2
        recommendService.addComment(recommendService.findOneByUserIdAndPostId(userId,postId).getScoreId());
        return ResultVOUtil.build(200,"success",map);
    }

}
