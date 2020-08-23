package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.config.AliyunConfig;
import com.ad.service.Impl.DocServiceImpl;
import com.ad.utils.ResultVOUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WENZHIKUN
 */

@Controller
@Slf4j
public class UploadController {

    @Autowired
    private AliyunConfig aliyunConfig;

    @Autowired
    private DocServiceImpl docService;



    @RequestMapping("/upload")
    @ResponseBody
    public ResultVO upload(@RequestParam(value = "file",required = false)MultipartFile file,
                           @RequestParam(value = "fileName",required = false)String fileName,
                           @RequestParam(value = "fileType",required = false)String fileType,
                           @RequestParam(value = "fileGrade",required = false)String fileGrade,
                           @RequestParam(value = "subject",required = false)String subject,
                           @RequestParam(value = "briefIntro",required = false)String briefIntro) throws IOException {
        //设置文件名 key
        //使用设置的文件名，以及原赖的文件格式
        String key = fileName +"."+ file.getOriginalFilename().split("\\.")[1];
        System.out.println(key);
        //1.查询文件是否存在
        //---->String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        String endpoint = aliyunConfig.getAliyunEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        //String accessKeyId = "LTAI4G7DhdL2WTDiUrjrkiW4";
        String accessKeyId = aliyunConfig.getAliyunAccessKeyId();
        //String accessKeySecret = "qrGx0wSkg5wF2NhDUwlpj3BMmg4joe";
        String accessKeySecret = aliyunConfig.getAliyunAccessKeySecret();
        //String bucket = "ad-share";
        String bucket = aliyunConfig.getAliyunBucket();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(bucket, key);
        //System.out.println(found);
        if(!found){
            //2.如果文件不存在就上传文件

            // 创建PutObjectRequest对象。
            //PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key,(File) file);

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            //ossClient.putObject(putObjectRequest);

            //上传文件流
            ossClient.putObject(bucket,key,file.getInputStream());
            System.out.println("file upload success !");

        }
        // 关闭OSSClient。
        ossClient.shutdown();

        return ResultVOUtil.build(200,"sucess","sucess");
    }
}
