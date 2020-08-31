package com.ad.controller;

import com.ad.VO.CommunityVO;
import com.ad.VO.ResultVO;
import com.ad.config.MySessionContext;
import com.ad.dto.PostDTO;
import com.ad.enums.ClassificationEnum;
import com.ad.pojo.UserInfo;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.RecommendServiceImpl;
import com.ad.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/18 22:43
 *
 * @version 1.0
 */
@Slf4j
@Controller
public class CommunityController {
    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private RecommendServiceImpl recommendService;

    @GetMapping("api/user/community")
    @ResponseBody
    public ResultVO community(@RequestParam("grade") int grade,
                              @RequestParam("classification") int classification,
                              @RequestParam("groupnum") int groupNum,
                              @RequestParam("groupsize") int groupSize,
                              HttpServletRequest request,
                              HttpServletResponse response){
        try {
            String sessionId = request.getHeader("Cookie").split("=")[1];
            UserInfo user = (UserInfo) MySessionContext.getSession(sessionId).getAttribute("userInfo");

            CommunityVO communityVO = new CommunityVO();
            //TODO fix it
            communityVO.setTotal(postService.countPost());

            List<PostDTO> postDTOList = null;
            //TODO if (grade = xxx) if(classification = xxx)
            if(classification== ClassificationEnum.ORDER_BY_TIME.getCode()){
                //"新鲜"栏目社区
                postDTOList = postService.findListOrderByTimeAndGrade(groupNum,groupSize,grade);
            }else if (classification == ClassificationEnum.ORDER_BY_MY.getCode()){
                //"我的"栏目社区
                postDTOList = postService.findListOrderByAuthor(groupNum,groupSize,user.getUserId());
                communityVO.setTotal(postService.countMyPost(user.getUserId()));
            }else if (classification == ClassificationEnum.ORDER_BY_SYS.getCode()){
                // "推荐"栏目社区
                postDTOList = recommendService.findListOrderByRecommend(groupNum,groupSize,user.getUserId(),grade);
            }else{
                log.error("请求参数错误,请检查设置");
            }

            if(postDTOList.isEmpty()){
                log.error("该栏目下话题数据为空,获取社区信息失败");
                return ResultVOUtil.errorMsg("该栏目下话题数据为空,获取社区信息失败");
            }

            communityVO.setPostDTOList(postDTOList);
            return ResultVOUtil.success(communityVO);

        }catch (Exception e){
            log.error("获取Cookie中的JSESSIONID失败或获取session中的用户信息失败！"+e.getMessage());
            return ResultVOUtil.errorMsg("通过sessionID获取用户信息失败");
        }
    }
}
