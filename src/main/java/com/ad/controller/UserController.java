package com.ad.controller;

/**
 * Create By  @林俊杰
 * 2020/8/1 22:24
 *
 * @version 1.0
 */


import com.ad.VO.ResultVO;
import com.ad.VO.UserInfoVO;
import com.ad.pojo.UserInfo;
import com.ad.utils.ResultVOUtil;
import com.ad.utils.WechatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Controller
public class UserController {

    /*@Autowired
    private UserMapper userMapper;*/

    /**
     * 微信用户登录详情
     */
    @PostMapping("wx/login")
    @ResponseBody
    public ResultVO user_login(@RequestParam(value = "code", required = false) String code,
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
        //TODO : user = this.userMapper.selectById(openid);替换掉下面那个null;
        UserInfo user = null;

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

            user = new UserInfo();
            user.setUserId(openid);
            user.setSessionKey(skey);
            //user.setUserMail(null);
            //user.setCreateTime(new Date());
            //user.setLastVisitTime(new Date());
            user.setUserMail(sessionKey);
            //user.setCity(city);
            //user.setProvince(province);
            //user.setCountry(country);
            user.setAvatarUrl(avatarUrl);
            user.setUserGender(Integer.parseInt(gender));
            user.setUserName(nickName);

            session.setAttribute("userInfo",user);
            System.out.println(user.toString());
        } else {
            // TODO :该用户已存在，刷新该用户最新登录时间到日志log
            System.out.println("用户登陆时间"+new Date());
            // 重新设置会话skey
            user.setSessionKey(skey);
            System.out.println(user.toString());
        }
        //encrypteData比rowData多了appid和openid
        //JSONObject userInfo = WechatUtil.getUserInfo(encrypteData, sessionKey, iv);
        //6. 把新的skey和sessionId返回给小程序
        UserInfoVO  userInfoVO = new UserInfoVO();
        userInfoVO.setSkey(skey);
        userInfoVO.setSessionId(sessionId);

        ResultVO result = ResultVOUtil.build(200, "登录成功", userInfoVO);
        System.out.println("------->数据="+result.getData().toString());
        return ResultVOUtil.build(200, "登录成功", userInfoVO);
    }

}



