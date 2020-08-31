package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.LinkInfo;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.service.Impl.LinkServiceImpl;
import com.ad.service.Impl.RecommendServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

/**
 * @author WenZhikun
 * @data 2020-08-28 16:01
 */
@Controller
@Slf4j
public class StarController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DocServiceImpl docService;

    @Autowired
    LinkServiceImpl linkService;

    @RequestMapping(value = "/api/star",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO star(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                         @RequestParam(value = "docId",required = false,defaultValue = "0")int docId,
                         @RequestParam(value = "isStar",required = false,defaultValue = "ture")boolean isStar){
        DocInfo docInfo = docService.findOneById(docId);
        if(!isStar){
            //如果没有收藏
            LinkInfo linkInfo = linkService.addStar(userId,docId);
            System.out.println(linkInfo);
            return ResultVOUtil.build(200,"收藏成功",true);
        }else{
            //如果已经收藏了，再点击则取消收藏
            linkService.cancelStar(userId,docId);
            return ResultVOUtil.build(200,"取消收藏",false);
        }

    }
}
