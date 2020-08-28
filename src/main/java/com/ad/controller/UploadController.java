package com.ad.controller;

import com.ad.VO.ResultVO;
import com.ad.VO.UploadVO;
import com.ad.config.AliyunConfig;
import com.ad.pojo.*;
import com.ad.service.Impl.*;
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

import javax.management.remote.rmi._RMIConnection_Stub;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Date;
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

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/upload")
    @ResponseBody
    public ResultVO upload(@RequestParam(value = "file",required = false)MultipartFile file,
                           @RequestParam(value = "fileName",required = false)String fileName,
                           @RequestParam(value = "fileType",required = false,defaultValue = "1")int fileType,
                           @RequestParam(value = "fileGrade",required = false)String fileGrade,
                           @RequestParam(value = "subject",required = false)String subject,
                           @RequestParam(value = "briefIntro",required = false)String briefIntro,
                           @RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                           @RequestParam(value = "postId",required = false,defaultValue = "0")int postId) throws IOException {
        if (fileName.length()>255){
            return ResultVOUtil.build(501,"error","文件名过长");
        }
        if (briefIntro.length()>255){
            return ResultVOUtil.build(501,"error","简介字数过长");
        }
        //设置文件的key
        System.out.println("测试------>文件大小"+file.getSize());
        String filePath = "adshare/" + userId + "/";
        String key = filePath + fileName +"."+ file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
        System.out.println("测试------------------>上传的文件名："+key);
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
            //如果文件不存在就上传
            //上传文件流
            ossClient.putObject(bucket,key,file.getInputStream());
            System.out.println("file upload success !");
        }else{
            System.out.println("The file is exist !");
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        DocInfo docInfo = docService.addDoc(fileName,fileType,(int)file.getSize(),filePath,userId);
        TagInfo tagInfoSubject = tagService.addTag(subject);
        TagInfo tagInfoGrade = tagService.addTag(fileGrade);
        //创建标签和文件的关联
        tagLinkService.addTagLinkToDoc(tagInfoSubject.getTagId(),docInfo.getDocId());
        tagLinkService.addTagLinkToDoc(tagInfoGrade.getTagId(),docInfo.getDocId());
        CommentInfo commentInfo;
        //用户发表的资源数量加一
        UserInfo userInfo = userService.findOneById(userId);
        userInfo.setDocNum(userInfo.getDocNum()+1);
        //如果postId是0说明是单独发表资源,进行帖子发表
        if (postId==0) {
            //说明帖子不存在，需要发表帖子
            PostInfo postInfo = postService.addPost(docInfo.getDocName(),briefIntro,userId);
            commentInfo = commentService.addComment(postInfo.getPostId(),userId,docInfo.getDocId(),1,briefIntro);
            postInfo.setCommentNum(0);
            postService.update(postInfo);
            //更新postId
            postId = postInfo.getPostId();
            //用户发表的帖子数量加一
           userInfo.setPostNum(userInfo.getPostNum()+1);
        }else{
            //评论类型为1，设置为附带资源类型
            commentInfo = commentService.addComment(postId,userId,docInfo.getDocId(),1,briefIntro);

        }
        userService.update(userInfo);
        log.info("ID为 " +userId +"的用户,于"+ new Date() +
                "上传了文件" + docInfo.getDocName()+" 到 "+docInfo.getDocPath() +"中");
        UploadVO uploadVO = new UploadVO();
        uploadVO.setCommentId(commentInfo.getCommentId());
        uploadVO.setDocId(docInfo.getDocId());
        uploadVO.setPostId(postId);

        return ResultVOUtil.build(200,"sucess",uploadVO);
    }
}
