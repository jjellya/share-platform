package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.pojo.CommentInfo;
import com.ad.pojo.DocInfo;
import com.ad.pojo.PostInfo;
import com.ad.service.Impl.CommentServiceImpl;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-30 17:28
 */
@Controller
public class GetResourse {

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private CommentServiceImpl commentService;

    @RequestMapping("/api/getres")
    @ResponseBody
    public ResultVO getres(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId){
        List<DocInfo>docInfoList = docService.findByUserId(userId);
        return ResultVOUtil.build(200,"success",docInfoList);
    }

    @RequestMapping("/api/userdoclinkpost")
    @ResponseBody
    public ResultVO connect(@RequestParam(value = "postId",required = false,defaultValue = "1")int postId,
                            @RequestParam(value = "docId",required = false,defaultValue = "1")int docId,
                            @RequestParam(value = "content",required = false,defaultValue = "")String content){
        DocInfo docInfo = docService.findOneById(docId);
        CommentInfo commentInfo = commentService.addComment(postId,docInfo.getUserId(),docId,1,content);
        return ResultVOUtil.build(200,"success",commentInfo.getCommentId());
    }
}
