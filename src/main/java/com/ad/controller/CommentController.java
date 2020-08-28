package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.pojo.CommentInfo;
import com.ad.service.Impl.CommentServiceImpl;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author WenZhikun
 * @data 2020-08-28 16:10
 */
@Controller
@Slf4j
public class CommentController {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping("/api/comment")
    @ResponseBody
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
        System.out.println(commentInfo);

        return ResultVOUtil.build(200,"success",commentInfo.getCommentId());
    }

}
