package com.ad.controller;

import com.ad.VO.AdvertisementVO;
import com.ad.VO.IndexPostVO;
import com.ad.VO.ResultVO;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.pojo.*;
import com.ad.service.Impl.*;
import com.ad.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-08-29 11:27
 */
@Controller
public class IndexController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RecommendServiceImpl recommendService;

    /*首页显示的帖子*/
    @RequestMapping("/index/getposts")
    @ResponseBody
    public ResultVO getPost(@RequestParam(value = "grade",required = false,defaultValue = "1")int grade,
                            @RequestParam(value = "groupNum",required = false,defaultValue = "1")int groupNum,
                            @RequestParam(value = "groupSize",required = false,defaultValue = "0")int groupSize){
        System.out.println(tagService.findOneById(55));
        if (groupSize==0){
            return ResultVOUtil.errorMsg("需要设定每组容量");
        }
        String gradeStr;
        if (grade==1) gradeStr="大一";
        else if (grade==2) gradeStr="大二";
        else if (grade==3) gradeStr="大三";
        else gradeStr="大四";
        List<TagInfo>tagInfoList = tagService.findByContent(gradeStr);
        //用来确定返回帖子的数量
        int n=0;
        if (tagInfoList.size()==0)
            return ResultVOUtil.build(200,"wuccess","无相关话题");
        List<TagLink> tagLinkList = new ArrayList<>();
        PostDTO postDTO = null;
        PostInfo postInfo = null;
        List<PostDTO>postDTOList = new ArrayList<>();
        UserInfo userInfo = null;

        List<TagLink>tagLinkList1 = new ArrayList<>();
        for (int i=0;i<tagInfoList.size();i++){
            tagLinkList = tagLinkService.findByTagId(tagInfoList.get(i).getTagId());
            for (int j=0;j<tagLinkList.size();j++){
                if (tagLinkList.get(j).getPostId()==null){
                    continue;
                }else {
                    postInfo = postService.findOneById(tagLinkList.get(j).getPostId());
                    userInfo = userService.findOneById(postInfo.getUserId());
                    tagLinkList1 = tagLinkService.findByPostId(postInfo.getPostId());
                    System.out.println("userId is "+ postInfo.getUserId());
                    System.out.println("postId is "+ postInfo.getPostId() +
                            "   tagLinkList's size is " + tagInfoList.size());
                    List<TagInfo>tagInfoList1 = new ArrayList<>();
                    for (int k =0 ;k<tagLinkList1.size();k++){
                        if (tagLinkList1.get(k).getTagId()==null||tagLinkList1.get(k)==null)
                            continue;
                        System.out.println("k = "+ k +" : "+tagLinkList1.get(k));
                        System.out.println("TagId is "+tagLinkList1.get(k).getTagId());
                        int id = tagLinkList1.get(k).getTagId();
                        System.out.println(tagService.findOneById(tagLinkList1.get(k).getTagId()));
                        tagInfoList1.add(tagService.findOneById(tagLinkList1.get(k).getTagId()));
                    }
                    postDTO = PostInfo2PostDTOConverter.convert(postInfo,userInfo,tagInfoList1);
                    n++;
                    postDTOList.add(postDTO);
                }
                if (n==groupNum*groupSize)
                    break;
            }
            if (n==groupNum*groupSize)
                break;
        }
        IndexPostVO indexPostVO = new IndexPostVO();
        indexPostVO.setPostDTOS(postDTOList);
        indexPostVO.setPostNumInGroup((n%groupSize==0)?groupSize:(n%groupSize));

        return ResultVOUtil.build(200,"success",indexPostVO);
    }

    @RequestMapping("/index/recommend")
    @ResponseBody
    public ResultVO recommend(@RequestParam(value = "userId",required = false,defaultValue = "1")int userId,
                              @RequestParam(value = "grade",required = false,defaultValue = "1")int grade,
                              @RequestParam(value = "index",required = false,defaultValue = "1")int index,
                              @RequestParam(value = "size",required = false,defaultValue = "0")int size){
        List<PostDTO> postDTOList = recommendService.findListOrderByRecommend(index,size,userId,grade);
        return ResultVOUtil.build(200,"success",postDTOList);
    }

    @RequestMapping("/index/advertisement")
    @ResponseBody
    private ResultVO advertisement(){

        AdvertisementVO advertisementVO = new AdvertisementVO();
        List<AdInfo>adInfoList = new ArrayList<>();
        //手动添加广告
        advertisementVO.setTotal(0);
        advertisementVO.setAdInfoList(adInfoList);

        return ResultVOUtil.build(200,"success",advertisementVO);
    }


}
