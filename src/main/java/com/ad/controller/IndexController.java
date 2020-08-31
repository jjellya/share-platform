package com.ad.controller;

import com.ad.VO.AdvertisementVO;
import com.ad.VO.IndexPostVO;
import com.ad.VO.ResultVO;
import com.ad.VO.SubjectTagVO;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.dto.TagDTO;
import com.ad.pojo.*;
import com.ad.service.Impl.*;
import com.ad.utils.ResultVOUtil;
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
 * @data 2020-08-29 11:27
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RecommendServiceImpl recommendService;

    @Autowired
    private AdvertisementServiceImpl advertisementService;

    /*首页显示的帖子*/
    @RequestMapping(value = "/index/getposts",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getPost(@RequestParam(value = "grade",required = false,defaultValue = "1")int grade,
                            @RequestParam(value = "groupNum",required = false,defaultValue = "1")int groupNum,
                            @RequestParam(value = "groupSize",required = false,defaultValue = "0")int groupSize){
        try{
            List<PostDTO> postDTOList = null;
            postDTOList = postService.findListOrderByTimeAndGrade(groupNum,groupSize,grade);
            if(postDTOList.isEmpty()){
                log.error("该栏目下话题数据为空,获取社区信息失败");
                return ResultVOUtil.errorMsg("该栏目下话题数据为空,获取社区信息失败");
            }

            IndexPostVO indexPostVO = new IndexPostVO();
            indexPostVO.setPostDTOS(postDTOList);
            indexPostVO.setTotal(postService.countPost());
            return ResultVOUtil.success(indexPostVO);
        }catch (Exception e){
            log.error("遇到未知错误:"+e.getMessage());
            return ResultVOUtil.errorMsg("遇到未知错误:"+e.getMessage());
        }
    }

    @RequestMapping(value = "/index/recommend",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO recommend(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                              @RequestParam(value = "grade",required = false,defaultValue = "1")int grade){
        List<TagDTO> tagDTOList = tagService.findListBySubject();
        SubjectTagVO subjectTagVO =  new SubjectTagVO();
        subjectTagVO.setTotal(tagDTOList.size());
        subjectTagVO.setTagDTOList(tagDTOList);
        return ResultVOUtil.success(subjectTagVO);
    }

    @RequestMapping(value = "/index/advertisement",method = RequestMethod.GET)
    @ResponseBody
    private ResultVO advertisement(@RequestParam(value = "size",required = false,defaultValue = "0")int size){

        AdvertisementVO advertisementVO = new AdvertisementVO();
        List<AdInfo> adInfoList = null;
        if (size<=0){
            return ResultVOUtil.errorMsg("请求数量错误");
        }
        try {
             adInfoList = advertisementService.findAdListOrderByTime(size);

        }catch (Exception e){
            log.error("数据库数据读取异常");
            return ResultVOUtil.errorMsg("数据库数据读取异常");
        }
        //手动添加广告
        advertisementVO.setTotal(adInfoList.size());
        advertisementVO.setAdInfoList(adInfoList);

        return ResultVOUtil.success(advertisementVO);
    }


}
