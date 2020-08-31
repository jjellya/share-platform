package com.ad.controller;


import com.ad.VO.ResultVO;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
import com.ad.service.Impl.UserServiceImpl;
import com.ad.utils.MyDateUtil;
import com.ad.utils.ResultVOUtil;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author WENZHIKUN
 */
@Controller
@Slf4j
public class AddPostController {

    //创建帖子

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/api/addpost",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO addPost(@RequestParam(value = "postTitle",required = false)String postTitle,
                            @RequestParam(value = "postContent",required = false)String postContent,
                            @RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                            @RequestParam(value = "postTag",required = false)List<String> postTag,
                            @RequestParam(value = "file",required = false) MultipartFile[] pictures) throws IOException {
        //判断是否接受到文件
        if (pictures==null){
            System.out.println("the file is null !");
        }else if (pictures.length>1){
            //文件限制一个
            return ResultVOUtil.build(501,"error","文件数量多");
        }
        //System.out.println(postContent);
        if (postContent.length()>255){
            //内容限制字数255个字
            return ResultVOUtil.build(501,"error","内容字数超标");
        }else if (postContent.length()==0){
            return ResultVOUtil.errorMsg("内容不能为空");
        }
        if(postTitle.length()>20){
            //标题限制字数20个字
            return ResultVOUtil.build(501,"error","题目长度超标");
        }else if (postTitle.length()==0){
            return ResultVOUtil.errorMsg("标题不能为空");
        }

        if (pictures!=null){
            MultipartFile picture = pictures[0];
            //图片的url
            String pUrl = null;
            //上传图片到SMMS图床
            //1、设置header，Authorization
            //2、发送请求
            //3、获取返回的图片url
            //在本地创建文件对象并删除
            String fileName = picture.getOriginalFilename();
            File f = new File(fileName);
            //获取文件流
            InputStream inputStream = picture.getInputStream();
            OutputStream outputStream = new FileOutputStream(f);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer,0,8192))!=-1){
                outputStream.write(buffer,0,bytesRead);
            }
            outputStream.close();
            inputStream.close();
            //System.out.println(f.getPath());

            //创建表头
            HashMap<String,String>header = new HashMap<>();
            header.put("Authorization","Fg3gacOdSFT2pArpjC72WQkQkEhPBffg");
            //创建参数列表
            HashMap<String,String>bodyFormat = new HashMap<>();
            bodyFormat.put("smfile",f.getPath());
            //api接口地址
            String url = "https://sm.ms/api/v2/upload";

            RequestBody body = returnBody(bodyFormat);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            Request.Builder builder = returnHeaders(header);
            Request request = builder.url(url)
                    .method("POST", body)
                    .addHeader("accept", "*/*")
                    .addHeader("connection", "Keep-Alive")
                    .addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String res = Objects.requireNonNull(response.body()).string();
                //System.out.println(res);
                //删除文件
                f.delete();
                JSONObject smms = JSONObject.fromObject(res);
                //判断图片是否已经上传
                if(!smms.getString("code").equals("success")){
                    pUrl = smms.getString("images");
                }else{
                    pUrl = smms.getJSONObject("data").getString("url");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(pUrl!=null){
                System.out.println("pUrl : "+pUrl);
                //将图片url加在内容后面
                postContent = postContent + "+" + pUrl;
            }
        }

        //创建帖子
        PostDTO postDTO = new PostDTO();
        PostInfo postInfo = postService.addPost(postTitle,postContent,userId);
        postInfo.setCommentNum(0);
        postService.update(postInfo);
        //创建标签列表
        //List<TagInfo>tagInfoList = new ArrayList<>();
        //为每个标签内容创建帖子
        for (int i=0;i<postTag.size();i++){
            TagInfo tagInfo = tagService.addTag(postTag.get(i));
            //创建标签和帖子链接
            tagLinkService.addTagLinkToPost(tagInfo.getTagId(),postInfo.getPostId());
        }

        //修改用户帖子信息
        UserInfo userInfo = userService.findOneById(userId);
        userInfo.setPostNum(userInfo.getPostNum()+1);
        userService.update(userInfo);
        log.info("用户: Id="+userInfo.getUserId()+",name = "+userInfo.getUserName()+"于 "+new Date()+
                " 帖子数加一，帖子数量变更为 "+ userInfo.getPostNum());
        System.out.println(userInfo.getPostNum());

        //进用户的年级添加到帖子标签中
        String gradeStr;
        if (userInfo.getUserGrade()==null || userInfo.getUserGrade() ==1) gradeStr="大一";
        else if (userInfo.getUserGrade()==2) gradeStr="大二";
        else if (userInfo.getUserGrade()==3) gradeStr="大三";
        else gradeStr="大四";

        TagInfo tagInfoGrade = tagService.addTag(gradeStr);
        tagLinkService.addTagLinkToPost(tagInfoGrade.getTagId(),postInfo.getPostId());
        Map<String,Integer>map = new HashMap<>();
        map.put("postId",postInfo.getPostId());
        return ResultVOUtil.build(200,"success",map);
    }





    /**
     * change bodyformat hashMap to RequestBody objects
     * @param bodyFormat the body hashMap, if use none, put("none",""), if form-data, put(key,value)
     * @return RequestBody
     */
    public static RequestBody returnBody(HashMap<String,String> bodyFormat)
    {

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");

        if(bodyFormat.containsKey("none"))
        {
            return RequestBody.create(mediaType, "");
        }
        else
        {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            Set<String> set = bodyFormat.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext())
            {
                String next;
                next = iterator.next();
                builder = builder.addFormDataPart("smfile",bodyFormat.get(next),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File(bodyFormat.get(next))));
            }
            return builder.build();
        }
    }

    /**
     * change hashMap header to Request.Builder object
     * @param header the hasHMap header
     * @return the Request.builder
     */
    public static Request.Builder returnHeaders(HashMap<String,String> header)
    {
        Request.Builder builder = new Request.Builder();
        Set<String> set = header.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext())
        {
            String next;
            next = iterator.next();
            builder.addHeader(next, header.get(next));
        }
        return builder;
    }


}
