package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.config.AliyunConfig;
import com.ad.pojo.DocInfo;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.ResultVOUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class DownLoadController {

    @Autowired
    private AliyunConfig aliyunConfig;

    @Autowired
    private DocServiceImpl docService;

    @Autowired
    private UserServiceImpl userService;
    @RequestMapping(value = "/download")
    @ResponseBody
    public ResultVO download(@RequestParam(value = "docId",required = false)int docId,
                             @RequestParam(value = "userId",required = false)int userId) throws IOException {
        DocInfo docInfo = docService.findOneById(docId);
        System.out.println(docInfo.toString());
        //UserInfo userInfo = userService.findOneById(userId);
        //通过文件ID获取文件的名称进行下载

        //OSS的访问地址
        //String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String endpoint = aliyunConfig.getAliyunEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        //String accessKeyId = "LTAI4G7DhdL2WTDiUrjrkiW4";
        String accessKeyId = aliyunConfig.getAliyunAccessKeyId();
        //String accessKeySecret = "qrGx0wSkg5wF2NhDUwlpj3BMmg4joe";
        String accessKeySecret = aliyunConfig.getAliyunAccessKeySecret();
        //String bucketName = "ad-share";
        String bucketName = aliyunConfig.getAliyunBucket();
        //<yourObjectName>表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String filePath = docInfo.getDocPath();
        String key = filePath + docInfo.getDocName();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 设置URL过期时间为10年
        //System.out.println(new Date().getTime());
        Date expiration = new Date(new Date().getTime()+60*1000);
        //System.out.println(expiration);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key , expiration);
        System.out.println("url : "+url);

        ossClient.shutdown();

        docInfo.setDownloadNum(docInfo.getDownloadNum()+1);
        docService.update(docInfo);
        log.info("ID为 "+userId + "的用户，于"+new Date() +
                "对文件" +docInfo.getDocPath()+ docInfo.getDocName() + "进行下载链接的获取,链接有效时间为3分钟。");
        Map<String,String>map = new HashMap<>();
        map.put("url",url.toString());
        return ResultVOUtil.build(200,"success",map);
    }
}
