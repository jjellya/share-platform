package com.ad.controller;

import com.ad.VO.PostDetailsVO;
import com.ad.VO.ResultVO;
import com.ad.converter.CommentInfo2CommentDTOConverter;
import com.ad.config.MySessionContext;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.CommentDTO;
import com.ad.dto.CommentDocDTO;
import com.ad.dto.DocDTO;
import com.ad.dto.PostDTO;
import com.ad.enums.ScoreEnum;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-23 19:42
 */
@Controller
@Slf4j
@Transactional
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
    private CommentServiceImpl commentService;

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private RecommendServiceImpl recommendService;

    @Autowired
    private LinkServiceImpl linkService;

    @RequestMapping(value = "/api/postdetails")
    @ResponseBody
    public ResultVO details(@RequestParam(value = "postId",required = false)int postId,
                            @RequestParam(value = "userId",required = false)int userId,
                            HttpServletRequest request){

        String sessionId = request.getHeader("Cookie").split("=")[1];
        UserInfo user_login = null;
        try {
            user_login = (UserInfo) MySessionContext.getSession(sessionId).getAttribute("userInfo");
        }catch (Exception e){
            log.error("用户cookie获取失败");
        }

        //创建帖子信息
        PostInfo postInfo = postService.findOneById(postId);
        PostDTO postDTO = new PostDTO();
        //贴主信息
        UserInfo userInfo = userService.findOneById(postInfo.getUserId());

        //通过postId获取tagLink
        List<TagLink>tagLinkList = tagLinkService.findByPostId(postId);
        System.out.println(tagLinkList.toString());
        List<TagInfo>tagInfoList = new ArrayList<>();
        if (tagLinkList!=null&&tagLinkList.size()>=1)
        for (int i=0;i<tagLinkList.size();i++){
            tagInfoList.add(tagService.findOneById(tagLinkList.get(i).getTagId()));
            System.out.println(tagInfoList.get(i).getTagId()+" : " +tagInfoList.get(i).getTagContent());
        }

        try {
            postDTO= PostInfo2PostDTOConverter.convert(postInfo,userInfo,tagInfoList);
        }catch (Exception e){
            log.error("话题DTO转换失败");
        }

        //该用户的信息
        UserInfo userInfoMe = userService.findOneById(userId);
        //该用户的收藏队列
        List<DocInfo>docInfoList = linkService.findDocListByUserId(userId);

        List<CommentInfo>commentInfoList = commentService.findByPostId(postId);
        List<CommentDTO>commentDTOList = new ArrayList<>();
        List<CommentDocDTO>commentDocDTOS = new ArrayList<>();
        CommentDTO commentDTO = null;
        CommentDocDTO commentDocDTO = null;
        UserInfo userInfo1 = null;
        DocInfo docInfo = null;
        boolean isStar = false;
        for (int i=0;i<commentInfoList.size();i++){
            isStar = false;
            userInfo1 = userService.findOneById(commentInfoList.get(i).getUserId());
            if (commentInfoList.get(i).getCommentType()==0) {
                //说明这个是没有带资源的评论
                commentDTO = CommentInfo2CommentDTOConverter.convert(userInfo1, commentInfoList.get(i));
                commentDTOList.add(commentDTO);
            }else {
                //带着资源的评论
                docInfo = docService.findOneById(commentInfoList.get(i).getDocId());
                if (docInfoList!=null||docInfoList.size()!=0)
                for (int j=0;j<docInfoList.size();j++){
                    if (docInfo.getDocId()==docInfoList.get(j).getDocId()){
                        isStar=true;
                        break;
                    }
                }
                commentDocDTO = CommentInfo2CommentDTOConverter.covertDoc(userInfo,commentInfoList.get(i),docInfo,isStar);
                commentDocDTOS.add(commentDocDTO);
            }
        }
        PostDetailsVO postDetailsVO = new PostDetailsVO();
        postDetailsVO.setPostDTO(postDTO);
        postDetailsVO.setCommentDTOList(commentDTOList);
        postDetailsVO.setCommentDocDTOList(commentDocDTOS);
        try{
            if (recommendService.findOneByUserIdAndPostId(user_login.getUserId(),postId)==null){
                recommendService.addScore(user_login.getUserId(),0,postId,0);
            }else {
                recommendService.clickAgain(recommendService.findOneByUserIdAndPostId(user_login.getUserId(),postId).getScoreId());
            }
        }catch (Exception e){
            log.error("用户点击行为检测失败");
        }

        return ResultVOUtil.build(200,"success",postDetailsVO);
    }
}
