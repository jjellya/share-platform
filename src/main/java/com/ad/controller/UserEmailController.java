package com.ad.controller;


import com.ad.VO.ResultVO;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class UserEmailController {
    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping(value = "/api/user/mail",method = RequestMethod.POST)
    public ResultVO userGrade(@RequestParam(value = "userId",required = false)int userId,
                              @RequestParam(value = "mail",required = false)String mail){
        //System.out.println(userId);
        UserInfo user = userService.findOneById(userId);
        user.setUserMail(mail);
        log.info("用户: Id="+user.getUserId()+",name = "+user.getUserName()+"于 "+new Date()+" 修改mail信息为 "+ user.getUserMail());
        userService.update(user);
        ResultVO resultVO = new ResultVO(200,"修改成功",user);
        System.out.println("后台数据 : " + resultVO.toString());

        return ResultVOUtil.build(200,"修改成功",user);
    }

}
