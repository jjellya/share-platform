package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
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
 * @data 2020-08-28 17:51
 */
@Controller
@Slf4j
public class SubjectController {

    @Autowired
    DocServiceImpl docService;

    @Autowired
    TagServiceImpl tagService;

    @Autowired
    TagLinkServiceImpl tagLinkService;

    @RequestMapping("/api/subject")
    @ResponseBody
    public ResultVO subject(@RequestParam(value = "subject",required = false)String subject,
                            @RequestParam(value = "year",required = false)String year){
        if (year.length()!=4){
            return ResultVOUtil.build(501,"error","年份信息错误，需要四位数的年份");
        }
        //找出带有subject标签对应的文件ID
        List<TagInfo>tagInfoList = tagService.findByContent(subject);
        if (tagInfoList.size()==0){
            return ResultVOUtil.build(200,"success","还没有人上传该科目的资源哦~");
        }
        //创建文件信息的列表
        List<DocInfo>docInfoList = new ArrayList<>();
        DocInfo docInfo = null;
        List<TagLink>tagLinkList = null;
        //选择处符合年份的文件信息
        for (int i=0;i<tagInfoList.size();i++){
            //获取关联该标签的列表
            tagLinkList = tagLinkService.findByTagId(tagInfoList.get(i).getTagId());
            for (int j=0;j<tagLinkList.size();j++){

            }
        }
        return ResultVOUtil.build(200,"success","success");
    }

}
