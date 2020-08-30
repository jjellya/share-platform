package com.ad.controller;

import com.ad.VO.DocDetailsVO;
import com.ad.VO.ResultVO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
import com.ad.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-30 17:06
 */
@Controller
public class DocDetails {

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @RequestMapping("/api/docdetails")
    @ResponseBody
    public ResultVO docdetails(@RequestParam(value = "docId",required = false,defaultValue = "1")int docId){

        DocInfo docInfo = docService.findOneById(docId);

        DocDetailsVO docDetailsVO = new DocDetailsVO();
        docDetailsVO.setDocInfo(docInfo);
        List<TagInfo>tagInfoList = new ArrayList<>();
        List<TagLink> tagLinkList = tagLinkService.findByDocId(docId);
        for (int i=0;i<tagLinkList.size();i++){
            if (tagLinkList.get(i).getTagId()==null){
                continue;
            }
            tagInfoList.add(tagService.findOneById(tagLinkList.get(i).getTagId()));
        }
        docDetailsVO.setTagInfoList(tagInfoList);
        return ResultVOUtil.build(200,"success",docDetailsVO);
    }
}
