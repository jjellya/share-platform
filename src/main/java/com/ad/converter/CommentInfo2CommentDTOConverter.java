package com.ad.converter;

import com.ad.dto.CommentDTO;
import com.ad.dto.CommentDocDTO;
import com.ad.dto.DocDTO;
import com.ad.pojo.CommentInfo;
import com.ad.pojo.DocInfo;
import com.ad.pojo.PostInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.service.UserService;
import com.ad.utils.MyDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-29 11:00
 */
@Slf4j
public class CommentInfo2CommentDTOConverter {

    //对于没有附带文件的评论
    public static CommentDTO convert(UserInfo userInfo, CommentInfo commentInfo){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAvatarUrl(userInfo.getAvatarUrl());
        commentDTO.setCommentId(commentInfo.getCommentId());
        commentDTO.setCommentType(commentInfo.getCommentType());
        commentDTO.setContent(commentInfo.getCommentContent());
        commentDTO.setPostId(commentInfo.getPostId());
        commentDTO.setUserId(commentInfo.getUserId());
        commentDTO.setUsername(userInfo.getUserName());
        if (commentInfo.getDocId()!=null)
            commentDTO.setDocId(commentInfo.getDocId());
        commentDTO.setCommentTime(MyDateUtil.convertTimeToFormat(commentInfo.getCommentTime().getTime()));
        return commentDTO;
    }

    //对于有文件的评论
    public static CommentDocDTO covertDoc(UserInfo userInfo,CommentInfo commentInfo,DocInfo docInfo,boolean isStar){
        CommentDocDTO commentDocDTO = new CommentDocDTO();
        commentDocDTO.setAvatarUrl(userInfo.getAvatarUrl());
        commentDocDTO.setCommentId(commentInfo.getCommentId());
        commentDocDTO.setCommentTime(MyDateUtil.convertTimeToFormat(commentInfo.getCommentTime().getTime()));
        commentDocDTO.setCommentType(commentInfo.getCommentType());
        commentDocDTO.setContent(commentInfo.getCommentContent());
        commentDocDTO.setDocId(commentInfo.getDocId());
        commentDocDTO.setDocName(docInfo.getDocName());
        commentDocDTO.setDocType(docInfo.getDocType());
        commentDocDTO.setDownloadNum(docInfo.getDownloadNum());
        commentDocDTO.setPostId(commentInfo.getPostId());
        commentDocDTO.setStarNum(docInfo.getStarNum());
        commentDocDTO.setUserId(commentInfo.getUserId());
        commentDocDTO.setUsername(userInfo.getUserName());
        //判断是否收藏
        commentDocDTO.setStar(isStar);
        return commentDocDTO;
    }


}
