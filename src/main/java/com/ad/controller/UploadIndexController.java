package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author WenZhikun
 * @data 2020-08-31 9:54
 */
@Controller
@Slf4j
public class UploadIndexController {



    /**
     * 上传界面的显示
     * @return
     */
    @RequestMapping("/uploadindex")
    @ResponseBody
    public ResultVO uploadindex(@RequestParam(value = "grade",required = false,defaultValue = "1")int grade){
        try{

            return ResultVOUtil.build(200,"success","success");
        }catch (Exception e){
            log.info("遇到未知错误" + e);
            return ResultVOUtil.errorMsg(e.toString());
        }
    }
}
