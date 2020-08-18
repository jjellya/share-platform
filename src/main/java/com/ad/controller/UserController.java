package com.ad.controller;

/**
 * Create By  @林俊杰
 * 2020/8/1 22:24
 *
 * @version 1.0
 */


import com.ad.VO.ResultVO;
import com.ad.VO.UserInfoVO;
import com.ad.VO.WechatUserVO;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import com.ad.utils.WechatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
public class UserController {


    @Autowired
    private UserServiceImpl userService;

    /**
     * 微信用户登录详情
     */
    @PostMapping("wx/login")
    @ResponseBody
    public ResultVO wx_user_login(@RequestParam(value = "code", required = false) String code,
                               @RequestParam(value = "rawData", required = false) String rawData,
                               @RequestParam(value = "signature", required = false) String signature,
                               @RequestParam(value = "encrypteData", required = false) String encrypteData,
                               @RequestParam(value = "iv", required = false) String iv,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code

        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);

        if (!signature.equals(signature2)) {
            return ResultVOUtil.build(500, "签名校验失败", null);
        }
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        UserInfo user = userService.findOneByOpenId(openid);

        // uuid生成唯一key以及sessionID , 用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();

        HttpSession session = request.getSession();
        String sessionId = session.getId();

        if (user == null) {
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");

            user = userService.addUser(openid,skey,avatarUrl,Integer.parseInt(gender),nickName);
            session.setAttribute("userInfo",user);


        } else {
            // TODO :该用户已存在，刷新该用户最新登录时间到日志log
            log.info("用户: Id="+user.getUserId()+",name = "+user.getUserName()+"于"+new Date()+"登录本平台");
            // 重新设置会话skey
            user.setSessionKey(skey);
            userService.update(user);
        }
        //encrypteData比rowData多了appid和openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);
        //6. 把新的skey和sessionId、userInfo返回给小程序
        WechatUserVO wechatUserVO = new WechatUserVO();
        wechatUserVO.setSkey(skey);
        wechatUserVO.setSessionId(sessionId);
        wechatUserVO.setOpenId(openid);
        UserInfoVO userInfoVO = new UserInfoVO(user);
        wechatUserVO.setUserInfoVO(userInfoVO);

        ResultVO result = ResultVOUtil.build(200, "登录成功", wechatUserVO);

        //TODO delete it!
        System.out.println("后台临时展示------->返回前端数据 ： "+result.getData().toString());
        return ResultVOUtil.build(200, "登录成功", wechatUserVO);
    }
}



