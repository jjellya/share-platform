package com.ad.controller;

import com.ad.VO.MyPostVO;
import com.ad.VO.MyResourseVO;
import com.ad.VO.MyStarVO;
import com.ad.VO.ResultVO;
import com.ad.converter.DocInfo2DocDTOConvert;
import com.ad.dto.DocDTO;
import com.ad.dto.PostDTO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.LinkInfo;
import com.ad.pojo.PostInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.*;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-31 9:58
 */
@Controller
@Slf4j
public class MyIndex {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private LinkServiceImpl linkService;

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TagServiceImpl tagService;

    @RequestMapping("/my/resourse")
    @ResponseBody
    public ResultVO myresourse(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                               @RequestParam(value = "type",required = false,defaultValue = "0")int type){
        //try{
            UserInfo userInfo = userService.findOneById(userId);
            List<DocInfo>docInfoList = docService.findByUserId(userId);
            List docDTOList = DocInfo2DocDTOConvert.selectDocType(docInfoList,type,userInfo);
            MyResourseVO myResourseVO = new MyResourseVO();
            myResourseVO.setDocDTOList(docDTOList);
            myResourseVO.setTotal(docDTOList.size());
            myResourseVO.setType(type);
            return ResultVOUtil.build(200,"success",myResourseVO);
//        }catch (Exception e){
//            log.info("遇到未知错误 : "+e);
//            return ResultVOUtil.errorMsg(e.toString());
//        }
    }

    @RequestMapping("/my/star")
    @ResponseBody
    public ResultVO star(@RequestParam(value = "user",required = false,defaultValue = "1")int userId){
        try{
            UserInfo userInfo = userService.findOneById(userId);
            List<DocInfo>docInfoList = linkService.findDocListByUserId(userId);
            List<DocDTO>docDTOList = DocInfo2DocDTOConvert.covertList(docInfoList,userInfo);
            MyStarVO myStarVO = new MyStarVO();
            myStarVO.setDocInfoList(docInfoList);
            myStarVO.setTotal(docDTOList.size());
            return ResultVOUtil.build(200,"success",myStarVO);
        }catch (Exception e){
            log.info("遇到未知错误 : "+e);
            return ResultVOUtil.errorMsg(e.toString());
        }
    }

    @RequestMapping("/my/posts")
    @ResponseBody
    public ResultVO myposts(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                            @RequestParam(value = "offset",required = false,defaultValue = "1")int offset,
                            @RequestParam(value = "size",required = false,defaultValue = "0")int size){
        try{
            List<PostDTO>postDTOList = postService.findListOrderByAuthor(offset,size,userId);
            MyPostVO myPostVO = new MyPostVO();
            myPostVO.setPostDTOList(postDTOList);
            myPostVO.setTotal(postDTOList.size());
            return ResultVOUtil.build(200,"status",myPostVO);
        }catch (Exception e){
            log.info("遇到未知错误 : "+e);
            return ResultVOUtil.errorMsg(e.toString());
        }
    }
}
