package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Create By  @林俊杰
 * 2020/7/31 20:18
 *
 * @version 1.0
 */
@Controller
public class HelloController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("hello")
    @ResponseBody
    public ResultVO hello( @RequestParam Integer userId, HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getHeader("Cookie").split("=")[1];

        System.out.println("------>进入该方法,sessionId = "+sessionId+";\nuserId = "+userId);
        //HttpSession session=request.getSession().getSessionContext().getSession(sessionId);
        //UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        ResultVO result = ResultVOUtil.build(200,"OK",userService.findOneById(userId));
        System.out.println(result);
        return result;
    }
}
