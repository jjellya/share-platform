package com.ad.service.Impl;

import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.mapper.PostMapper;
import com.ad.mapper.TagMapper;
import com.ad.mapper.UserMapper;
import com.ad.mapper.UserScoreMapper;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.pojo.UserScore;
import com.ad.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/21 20:07
 *
 * @version 1.0
 */
@Service
@Slf4j
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private UserScoreMapper scoreMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<PostDTO> findListOrderByRecommend(int offset, int size, int userId) {
        List<PostDTO> postDTOList = new ArrayList<>();

        if(size == 0){
            log.error("请求错误,size = 0");
            return postDTOList;
        }

        UserInfo tempUser;
        List<TagInfo> tempTagList;

        //Core
        //1.首先把该用户的浏览过(即有评分的)的数据集提取出来
        int avgUserScore = 0;
        int sumUserScore = 0;
        List<UserScore> userScoreList = scoreMapper.getExistScoreByUserId(userId);

        HashMap<Integer,Integer> userScoreMap = new HashMap<>();
        List<Integer> userPostIdList = new ArrayList<>();
        for (UserScore userScore:userScoreList
             ) {
            userPostIdList.add(userScore.getPostId());
            sumUserScore+=userScore.getScoreValue();
            userScoreMap.put(userScore.getPostId(),userScore.getScoreValue());
        }

        avgUserScore=sumUserScore/userPostIdList.size();
        System.out.println("user's avg = "+avgUserScore);

        System.out.println("user's actor--->"+userScoreMap.toString());



        //2.把有相同浏览记录的用户的评分扒拉出来
        int compareUserNum = 0;
        int browsedPostNum = 0;
        List<List<UserScore>> commonPostScoreList = new ArrayList<>();

        for (Integer postId:userPostIdList
             ) {
            browsedPostNum++;
            commonPostScoreList.add(scoreMapper.getUserScoreByPostId(postId));
        }

        //化为矩阵,Map<用户,话题Id,评分>
        HashMap<Integer,List<HashMap<Integer,Integer>>> othersScoreMap = new HashMap<>();

        List<Integer> othersUserList = new ArrayList<>();

        HashMap<Integer,Integer> avgOthersScore = new HashMap<>();


            //与user相同的各个postId的对应的评分记录list
        for (List<UserScore> commonPostList:commonPostScoreList
             ) {

            //获取非零有交集的其他用户数量
            compareUserNum=commonPostList.size()>compareUserNum?commonPostList.size():compareUserNum;

            //相同postId的评分记录
            for (UserScore commonScore:commonPostList
            ) {

                //自己不要进去
                if(commonScore.getUserId()==userId) continue;

                HashMap<Integer,Integer> tempScoreMap = new HashMap<>();
                List<HashMap<Integer,Integer>> lastMapList ;
                tempScoreMap.put(commonScore.getPostId(),commonScore.getScoreValue());

                if(!othersUserList.contains(commonScore.getUserId())){
                    avgOthersScore.put(commonScore.getUserId(),commonScore.getScoreValue());
                    othersUserList.add(commonScore.getUserId());
                    lastMapList = new ArrayList<>();
                }else {
                    int tempSum = avgOthersScore.get(commonScore.getUserId())+commonScore.getScoreValue();
                    avgOthersScore.put(commonScore.getUserId(),tempSum);
                    lastMapList = othersScoreMap.get(commonScore.getUserId());
                }

                    lastMapList.add(tempScoreMap);

                othersScoreMap.put(commonScore.getUserId(),lastMapList);

            }
            System.out.println();
        }

        for (Integer otherUserId:othersUserList
             ) {
            System.out.println(otherUserId+"的总分 = "+avgOthersScore.get(otherUserId));
            int avgTemp = avgOthersScore.get(otherUserId)/othersScoreMap.get(otherUserId).size();
            avgOthersScore.put(otherUserId,avgTemp);
        }

        //TODO delete it！
        System.out.println("需要比较的用户集数量 = "+compareUserNum);
        System.out.println("other用户列表长度="+othersUserList.size());
        System.out.println("otherScoreMap  = "+othersScoreMap.toString());
        for (Integer otherUserId: othersUserList
             ) {

            System.out.print("------->"+ otherUserId+":  "+othersScoreMap.get(otherUserId).toString());
            System.out.print(" , map.size = "+othersScoreMap.get(otherUserId).size());
            System.out.println(",  平均评分 = "+avgOthersScore.get(otherUserId));
        }


        //TODO:3.计算Pearson相关系数





        List<PostInfo> postList = new ArrayList<>();
       // List<PostInfo> postList = postMapper.getPageOrderByTime(offset,size);
        if(postList.size()>0) {
            for (PostInfo post : postList
            ) {
                tempUser = userMapper.getUserById(post.getUserId());
                tempTagList = tagMapper.getTagByPostId(post.getPostId());
                postDTOList.add(PostInfo2PostDTOConverter.convert(post,tempUser,tempTagList));
            }
        }

        return postDTOList;
    }
}
