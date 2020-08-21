package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.VO.UserInfoVO;
import com.ad.VO.YibanUserVO;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import com.ad.simulateLogin.YiBanConstent;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static com.ad.simulateLogin.YiBanConstent.yibanUserVO;

/**
 * @author WENZHIKUN
 */

@Controller
@Slf4j
public class YiBanController {
    /**
     *  易班登录
     */
    @Autowired
    private UserServiceImpl userService;

    //appId
    String client_id = "64553ae7b9c472d3";
    //appSecret
    String client_secret = "f9dc5d5205169423f66b455420c45c5e";
    //回调地址
    String redirect_uri = "http://localhost:8088/adshare/callback";
    //获取 access_token 地址
    String tolen_url = "https://openapi.yiban.cn/oauth/access_token";
    //sessionId
    String sessionId;
    //用户凭证
    String accessToken;
    //用户Id
    String userid;

    @RequestMapping("/callback")
    @ResponseBody
    public ResultVO user_login(@RequestParam(name = "code",required = false)String code,
                               @RequestParam(name = "state",required = false)String state){
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        //添加访问请求数据 form-data 形式
        params.add("client_id", client_id);
        params.add("client_secret", client_secret);
        params.add("code", code);
        params.add("redirect_uri",redirect_uri);
        String str = sendPostFormRequest(params);
        JSONObject jsonObject = JSONObject.fromObject(str);
        //获取用户AccessToken 和 userId
        if (jsonObject.has("access_token")){
            accessToken = jsonObject.getString("access_token");
            userid = jsonObject.getString("userid");
        }
        //System.out.println("测试 accessToken : "+accessToken);
        //通过access_token 获取用户信息 user/me
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://openapi.yiban.cn/user/me?access_token="+ accessToken)
                .build();
        JSONObject jobj_me = null;
        try {
            //1.通过get请求返回string类型的用户数据
            //2.将string类型用户数据转为json类型进行处理
            Response response = client.newCall(request).execute();
            //string 类型用户数据
            String string = response.body().string();
            //转为json类型，获取用户info
            jobj_me = JSONObject.fromObject(string).getJSONObject("info");
            System.out.println("后台参数，用户数据："+jobj_me);
        } catch (IOException e) {
            return null;
        }
        /* 无权访问
        //通过access_token 获取用户认证信息 user/verify_me
        request = new Request.Builder()
                .url("https://openapi.yiban.cn/user/verify_me?access_token="+ accessToken)
                .build();
        JSONObject jobj_verify = null;
        try {
            //1.通过get请求返回string类型的用户数据
            //2.将string类型用户数据转为json类型进行处理
            Response response = client.newCall(request).execute();
            //string 类型用户数据
            String string = response.body().string();
            //转为json类型，获取用户info
            jobj_verify = JSONObject.fromObject(string).getJSONObject("info");
            System.out.println("后台参数，用户数据："+jobj_verify);
        } catch (IOException e) {
            return null;
        }

         */

        //判断用户是不是新用户？
        String openid = jobj_me.getString("yb_userid");
        System.out.println("openid = "+openid);
        UserInfo userInfo = userService.findOneByOpenId(openid);
        HttpServletRequest re = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取sessionId
        HttpSession session = re.getSession();
        sessionId = session.getId();
        if(userInfo==null){
            //获取当前年份
            Calendar date = Calendar.getInstance();
            int currentYear = date.get(Calendar.YEAR);
            //存入用户信息
            String nickName = jobj_me.getString("yb_usernick");//用户真实姓名
            String avatarUrl = jobj_me.getString("yb_userhead");//用户头像
            //int user_grade = currentYear - Integer.parseInt(jobj_verify.getString("yb_enteryear"));
            int gender;//用户性别c
            if(jobj_me.getString("yb_sex").compareTo("M")==0)
                gender=0;
            else gender=1;

            userInfo = userService.addUser(openid,accessToken,avatarUrl,gender,nickName);
            session.setAttribute("userInfo",userService);
        }else{
            log.info("用户: Id="+userInfo.getUserId()+",name = "+userInfo.getUserName()+"于"+new Date()+"登录本平台");
            // 重新设置会话accesstoken
            userInfo.setSessionKey(accessToken);
            userService.update(userInfo);
        }
        yibanUserVO = new YibanUserVO();
        yibanUserVO.setCode(code);
        yibanUserVO.setAccesstoken(accessToken);
        yibanUserVO.setOpenId(openid);
        yibanUserVO.setSessionid(sessionId);
        UserInfoVO userInfoVO = new UserInfoVO(userInfo);
        yibanUserVO.setUserInfoVO(userInfoVO);

        ResultVO resultVO = ResultVOUtil.build(200, "登录成功", yibanUserVO);

        System.out.println("后台临时展示------->返回前端数据 ： "+resultVO.getData().toString());
        return ResultVOUtil.build(200, "登录成功", yibanUserVO);
    }

    public YibanUserVO getYibanUserVO() {
        return yibanUserVO;
    }

    //使用post方法发送form-data
    private String sendPostFormRequest(MultiValueMap<String, String> params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        //表单方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = client.exchange(tolen_url, method, requestEntity, String.class);
        //System.out.println(response.getBody());
        return response.getBody();
    }
}
