package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.config.AliyunConfig;
import com.ad.utils.ResultVOUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class DownLoadController {

    @Autowired
    private AliyunConfig aliyunConfig;

    @RequestMapping("/download")
    @ResponseBody
    public ResultVO download(@RequestParam(value = "docId",required = false)int docId,
                             @RequestParam(value = "userId",required = false)int userId) throws IOException {
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
        String objectName = "Hello.java";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("D:\\下载\\"+objectName));
        System.out.println("DownLoad Success !");

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);

        // 读取文件内容。
        System.out.println("Object content:");
        BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\n" + line);
        }
        // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
        reader.close();

        // 关闭OSSClient。
        ossClient.shutdown();


        return ResultVOUtil.build(200,"success","success");
    }
}
