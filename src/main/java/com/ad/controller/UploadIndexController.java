package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.dto.PostDTO;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WenZhikun
 * @data 2020-08-31 9:54
 */
@Controller
@Slf4j
public class UploadIndexController {
    @Autowired
    private PostServiceImpl postService;

    /**
     * 上传界面的显示
     * @return
     */
    @RequestMapping(value = "/uploadindex",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO uploadindex(@RequestParam(value = "grade",required = false,defaultValue = "1")int grade,
                                @RequestParam(value = "offset",required = false,defaultValue = "1")int offset,
                                @RequestParam(value = "size",required = false,defaultValue = "0")int size){
        try{
            List<PostDTO>list = postService.findListOrderByGradeWithOutDoc(offset,size,grade);
            Map<String,Object>map = new HashMap<>();
            map.put("postDTOList",list);
            return ResultVOUtil.build(200,"success",map);
        }catch (Exception e){
            log.info("遇到未知错误" + e);
            return ResultVOUtil.errorMsg(e.toString());
        }
    }
}
