package com.ad.controller;

import com.ad.simulateLogin.Http;
import com.ad.simulateLogin.HttpConfig;
import com.ad.simulateLogin.Params;
import com.ad.simulateLogin.impl.Base64;
import com.ad.simulateLogin.impl.RSAUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.Map;

@Controller
public class SimulatLogin {
    String client_id = "64553ae7b9c472d3";
    String redirect_uri = "http://localhost:8088/adshare/callback";
    String state = "STATE";
    String spoce = "1,2,3,";
    String display = "html";
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public String simulat(@RequestParam Map<String,String>map) throws Exception {
        //System.out.println(map.get("account"));
        // 首先获取到登陆页面的网页内容
        HttpConfig config = new HttpConfig("https://openapi.yiban.cn/oauth/authorize?client_id=64553ae7b9c472d3&redirect_uri=http://localhost:8088/adshare/callback&state=STATE");//请求的地址
        byte[] re = Http.get(config);
        String html = new String(re);

        // 之后解析网页内容
        Document dom = Jsoup.parse(html);
        // 这里是获取到公钥所在位置的节点信息
        Element login_pr = dom.getElementById("key");
        // 取出公钥信息
        String keys = login_pr.attr("value");
        // 去除公钥信息的多余内容，
        String publicKey = keys.substring(26, keys.length() - 26).replaceAll("\n", "").replaceAll("\r", "");
        //System.out.println("服务器返回的COOKIE信息：" + config.getBackHeaderCookieString());
        //System.out.println("publicKey:" + publicKey);
        String account = map.get("account");
        String password = map.get("password");
        // 加密密码
        String password2 = enCode(password, publicKey);
        //System.out.println("加密后的密码：" + password2);
        // 沿用上一次的访问配置信息，里面存有上一次访问时服务器返回的COOKIE信息
        config.setUrl("https://oauth.yiban.cn/code/usersure"); //修改post请求访问的网站
        // 设置参数
        Params params = new Params();
        params.add("oauth_uname", account);
        params.add("oauth_upwd", password2);
        //params.add("captcha", "");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("state", state);
        params.add("scope", spoce);
        params.add("display", display);
        //params.add("keysTime", login_pr.attr("data-keys-time"));
        // 把参数加入到访问配置里面
        config.setData(params.getParams());
        config.setAjax(true);// 设置ajax异步信息
        // 设置页面路径来源，之所以要设置，因为我的目的是完全模仿浏览器行为，你可以参照调试工具的请求信息按需设置
        config.setReferer("https://oauth.yiban.cn/code/html?client_id=64553ae7b9c472d3&redirect_uri=http://localhost:8088/adshare/callback&state=STATE");
        //System.out.println("发送的参数：" + config.getData());
        // 执行网络请求
        re = Http.post(config);
        String jsonStr = new String(re);
        // 因为返回的是一个json格式字符串，而且其中汉字内容还是经过编码的，已我们的大脑看编码过后的内容时很难看得懂的
        // 所以只能把json字符串解析一下，之后再输入，解析后它会自动转换中文内容为我们可识别的汉字
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //System.out.println("模拟登录返回测试数据 ---> "+jsonObject);
        //获取授权url
        String reUrl = jsonObject.getString("reUrl");
        //System.out.println(reUrl);
        RestTemplate restTemplate = new RestTemplate();
        //访问reUrl
        restTemplate.getForEntity(reUrl,null);
        // System.out.println(html);
        return null;//无返回界面
    }
    public static String enCode(String password,String publicKey){
        String re = null;
        try {
            byte[] bytes = RSAUtils.encryptData(password.getBytes(),Base64.getInstance().Decryption(publicKey));
            // Base64.getInstance().Decryption(publicKey) 把公钥通过Base64解密为byte字节

            re = Base64.getInstance().EncryptionToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }
}

