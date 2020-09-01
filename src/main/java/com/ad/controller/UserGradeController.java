package com.ad.controller;


import com.ad.VO.ResultVO;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.service.UserService;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class UserGradeController {
    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @RequestMapping(value = "/user/grade")
    public ResultVO userGrade(@RequestParam(value = "userId",required = false)int userId,
                              @RequestParam(value = "grade",required = false)int grade){
        //System.out.println(userId);
        UserInfo user = userService.findOneById(userId);
        //获取当前年份
        Calendar data = Calendar.getInstance();
        int currentYear = data.get(Calendar.YEAR);
        //判断年级是否合法
        if (grade>=1&&grade<=4) {
            //设置年级
            user.setUserGrade(grade);
        }else if (grade>4){
            //输入有误
            user.setUserGrade(4);
        }else{
            user.setUserGrade(1);
        }
        log.info("用户: Id="+user.getUserId()+",name = "+user.getUserName()+"于 "+new Date()+" 修改年级信息为 "+ user.getUserGrade());
        userService.update(user);
        ResultVO resultVO = new ResultVO(200,"修改成功",user);
        System.out.println("后台数据 : " + resultVO.toString());
        Map<String,UserInfo>map = new HashMap<>();
        map.put("userInfo",user);
        return ResultVOUtil.build(200,"修改成功",map);
    }
}
