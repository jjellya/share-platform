package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.dto.TagDTO;
import com.ad.service.Impl.TagServiceImpl;
import com.ad.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WenZhikun
 * @data 2020-09-01 23:52
 */
@Controller
public class GetSubjectByTag {
    @Autowired
    private TagServiceImpl tagService;

    @RequestMapping("/upload/getsubject")
    @ResponseBody
    public ResultVO getsubject(){
        List<TagDTO>tagDTOList = tagService.findListBySubject();
        for (TagDTO tagDTO : tagDTOList){
            System.out.println(tagDTO);
        }
        Map<String,Object>map = new HashMap<>();
        map.put("tagDTOList",tagDTOList);
        return ResultVOUtil.success(tagDTOList);
    }
}
