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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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

    @RequestMapping(value = "/api/subject",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO subject(@RequestParam(value = "subject",required = false)String subject,
                            @RequestParam(value = "year",required = false)String year){
        if (year.length()!=4){
            return ResultVOUtil.build(501,"error","年份信息错误，需要四位数的年份");
        }else if (Integer.parseInt(year)>(Calendar.getInstance().get(Calendar.YEAR))){
            return ResultVOUtil.build(200,"success","想看未来的题目？不存在的~");
        }else if (Integer.parseInt(year)<2020){
            return ResultVOUtil.build(200,"success","年代过于久远，您想寻找的资源已穿越~");
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
        Calendar calendar = Calendar.getInstance();
        //选择处符合年份的文件信息
        for (int i=0;i<tagInfoList.size();i++){
            //获取关联该标签的列表
            tagLinkList = tagLinkService.findByTagId(tagInfoList.get(i).getTagId());
            if (tagLinkList.size()==0)
                continue;
            //System.out.println("size = "+tagLinkList.size());
            for (int j=0;j<tagLinkList.size();j++){
                //System.out.println(j);
                if (tagLinkList.get(j).getDocId()==null){
                    //System.out.println("------------------------true-----------------------");
                    if (i==tagLinkList.size()-1)
                        break;
                    else continue;
                }
                //System.out.println("后台测试：-------------------> "+tagLinkList.get(j));
                docInfo = docService.findOneById(tagLinkList.get(j).getDocId());
                //System.out.println(docInfo);
                //System.out.println(docInfo.getUploadTime().getYear());
                calendar.setTime(docInfo.getUploadTime());
                //System.out.println(calendar.get(Calendar.YEAR));
                if (calendar.get(Calendar.YEAR)==Integer.parseInt(year)){
                    docInfoList.add(docInfo);
                    System.out.println("符号时间要求的文件------------>"+docInfo);
                }
            }
        }
        if (docInfoList.size()==0){
            return ResultVOUtil.build(200,"success","not found");
        }
        Map<String,Object>map = new HashMap<>();
        map.put("docInfoList",docInfoList);
        return ResultVOUtil.build(200,"success",map);
    }

}
