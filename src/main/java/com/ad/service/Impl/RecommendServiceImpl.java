package com.ad.service.Impl;

import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.PostDTO;
import com.ad.enums.ScoreEnum;
import com.ad.mapper.PostMapper;
import com.ad.mapper.TagMapper;
import com.ad.mapper.UserMapper;
import com.ad.mapper.UserScoreMapper;
import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.UserInfo;
import com.ad.pojo.UserScore;
import com.ad.service.RecommendService;
import com.ad.utils.MyDateUtil;
import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private PostServiceImpl postService;

    @Override
    public List<PostDTO> findListOrderByRecommend(int offset, int size, int userId,int grade) {
        List<PostDTO> postDTOList = new ArrayList<>();

        if(size == 0){
            log.error("请求错误,size = 0");
            return postDTOList;
        }

        UserInfo tempUser;
        List<TagInfo> tempTagList;

        //Core
        //1.首先把该用户的浏览过(即有评分的)的数据集提取出来
        float avgUserScore = 0f;
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
        //System.out.println("user's avg = "+avgUserScore);

        //System.out.println("user's actor--->"+userScoreMap.toString());



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
        HashMap<Integer,HashMap<Integer,Integer>> othersScoreMap = new HashMap<>();

        List<Integer> othersUserList = new ArrayList<>();

        HashMap<Integer,Float> avgOthersScore = new HashMap<>();


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

                HashMap<Integer,Integer> lastScoreMap ;

                if(!othersUserList.contains(commonScore.getUserId())){
                    avgOthersScore.put(commonScore.getUserId(),(float)commonScore.getScoreValue());
                    othersUserList.add(commonScore.getUserId());
                    lastScoreMap =  new HashMap<>();
                }else {
                    float tempSum = avgOthersScore.get(commonScore.getUserId())+commonScore.getScoreValue();
                    avgOthersScore.put(commonScore.getUserId(),tempSum);
                    lastScoreMap = othersScoreMap.get(commonScore.getUserId());
                }

                lastScoreMap.put(commonScore.getPostId(),commonScore.getScoreValue());

                othersScoreMap.put(commonScore.getUserId(),lastScoreMap);

            }
            //System.out.println();
        }

        for (Integer otherUserId:othersUserList
             ) {
            //System.out.println(otherUserId+"的总分 = "+avgOthersScore.get(otherUserId));
            float avgTemp = avgOthersScore.get(otherUserId)/othersScoreMap.get(otherUserId).size();
            avgOthersScore.put(otherUserId,avgTemp);
        }


//        System.out.println("需要比较的用户集数量 = "+compareUserNum);
//        System.out.println("other用户列表长度="+othersUserList.size());
//        System.out.println("otherScoreMap  = "+othersScoreMap.toString());
//        for (Integer otherUserId: othersUserList
//             ) {
//
//            System.out.print("------->"+ otherUserId+":  "+othersScoreMap.get(otherUserId).toString());
//            System.out.print(" , map.size = "+othersScoreMap.get(otherUserId).size());
//            System.out.println(",  平均评分 = "+avgOthersScore.get(otherUserId));
//        }


        //3.计算Pearson相关系数
        HashMap<Integer,Double> pearsonMap = new HashMap<>();
        HashMap<Integer,Double> neighbourUserMap = new HashMap<>();
        List<Integer> neighbourUserIdList =  new ArrayList<>();
        Double minsimilarPearson = 65535.0;
        Integer minsimilarPearsonUserId = 0;
        int calculateCount = 0;

        for (Integer otherUserId:othersUserList
             ) {
            //不要算太多了
            if(neighbourUserMap.size()>10&&calculateCount>100) break;

            if (othersScoreMap.get(otherUserId).size()>2){//小于2个相同话题的用户没有借鉴价值
                calculateCount++;
                float avgScore = avgOthersScore.get(otherUserId);
                float sumUpScore = 0f;
                float sumDownScoreA = 0f;
                float sumDownScoreB = 0f;

                HashMap<Integer,Integer> anotherScoreMap = othersScoreMap.get(otherUserId);

                for (Integer postId: userPostIdList
                ) {
                    if (anotherScoreMap.containsKey(postId)){
                        //总算开始计算了!啦啦啦啦~

                        //计算分子的值
                        sumUpScore += (anotherScoreMap.get(postId)-avgScore)*(userScoreMap.get(postId)-avgUserScore);
                        //计算分母的值
                        sumDownScoreA += (anotherScoreMap.get(postId)-avgScore)*(anotherScoreMap.get(postId)-avgScore);
                        sumDownScoreB += (userScoreMap.get(postId)-avgUserScore)*(userScoreMap.get(postId)-avgUserScore);
                    }
                }

                double pearson = sumUpScore/(Math.sqrt(sumDownScoreA)*Math.sqrt(sumDownScoreB));

                //TODO,Pearson系数大于等于0.8,建议写入数据库以节省计算开销
                if(neighbourUserMap.size()<=10){//选择10个邻居用户即可,太多的话会影响准确度，而且造成计算资源浪费
                    if(pearson>0.60){
                        neighbourUserMap.put(otherUserId,pearson);
                        neighbourUserIdList.add(otherUserId);
                        minsimilarPearson= pearson<minsimilarPearson?pearson:minsimilarPearson;
                        minsimilarPearsonUserId = otherUserId;
                    }
                }else if(pearson> minsimilarPearson){
                    neighbourUserMap.remove(minsimilarPearsonUserId);
                    neighbourUserIdList.remove(minsimilarPearsonUserId);
                    neighbourUserMap.put(otherUserId,pearson);
                }
                pearsonMap.put(otherUserId,pearson);
            }
            //System.out.println("用户"+otherUserId+"的Pearson系数："+pearsonMap.get(otherUserId));
        }

        //System.out.println("选取的相邻用户 ："+neighbourUserMap.toString());

        //4.获取推荐内容话题
        HashMap<Integer,HashMap<Integer,Integer>> neverBrowsedScoreMap = new HashMap<>();//map<postId,map<userId,score>>
        List<Integer> neverBrowsedPostList = new ArrayList<>();
        //获取该用户从未浏览过的post列表
        for (Integer otherUserId:neighbourUserIdList
             ) {
            List<UserScore> neighbourScoreList = scoreMapper.getUserScoreByUserId(otherUserId);
            for (UserScore userS:neighbourScoreList
                 ) {
               if(!userPostIdList.contains(userS.getPostId())){
                   HashMap<Integer,Integer> tempNeighbourMap;

                   if (!neverBrowsedPostList.contains(userS.getPostId())){
                       neverBrowsedPostList.add(userS.getPostId());
                       tempNeighbourMap = new HashMap<>();
                   }else {
                       tempNeighbourMap = neverBrowsedScoreMap.get(userS.getPostId());
                   }
                    tempNeighbourMap.put(userS.getUserId(),userS.getScoreValue());
                   neverBrowsedScoreMap.put(userS.getPostId(),tempNeighbourMap);

               }
            }
        }
        System.out.println("用户没有浏览过的话题:"+neverBrowsedScoreMap.toString());

        //计算预测分数
        List<Integer> recommendPostIdList = new ArrayList<>();
        double sumUpPred = 0f;
        double sumDownPred = 0f;
        for (Integer neverBrowsedPostId:neverBrowsedPostList
             ) {
            HashMap<Integer,Integer> tempNeverBrowsedPostScoreMap = neverBrowsedScoreMap.get(neverBrowsedPostId);

            for (Integer neighbourUserId: neighbourUserIdList
                 ) {
                if (tempNeverBrowsedPostScoreMap.containsKey(neighbourUserId)){
                    sumUpPred += (tempNeverBrowsedPostScoreMap.get(neighbourUserId)-avgOthersScore.get(neighbourUserId))*neighbourUserMap.get(neighbourUserId);
                   // System.out.println("avg = "+avgOthersScore.get(neighbourUserId)+",up = "+sumUpPred);
                    sumDownPred += neighbourUserMap.get(neighbourUserId);
                   // System.out.println("down= "+sumDownPred);
                }

            }
            double pred = avgUserScore+(sumUpPred/sumDownPred);
            //System.out.println("话题"+neverBrowsedPostId+"的预测分数 = "+pred);
            if (pred>avgUserScore+0.5||pred>5){
                recommendPostIdList.add(neverBrowsedPostId);
            }
        }


        //List<PostInfo> postList = new ArrayList<>();
       // List<PostInfo> postList = postMapper.getPageOrderByTime(offset,size);
        int returnNum = 0;
        if(recommendPostIdList.size()>0) {
            for (Integer postId : recommendPostIdList
            ) {
                if (returnNum>size) break;
                returnNum++;
                PostInfo post = postMapper.getPostById(postId);
                tempUser = userMapper.getUserById(post.getUserId());
                tempTagList = tagMapper.getTagByPostId(postId);
                postDTOList.add(PostInfo2PostDTOConverter.convert(post,tempUser,tempTagList));
            }
        }

        if(postDTOList.size()<size){
            postDTOList.addAll(postService.findListOrderByTimeAndGrade(1,size-postDTOList.size(),grade));
        }

        //System.out.println(postDTOList.toString());
        return postDTOList;
    }

    @Override
    @Transactional
    public UserScore addScore(int userId, int scoreType,@Nullable int postId,@Nullable int docId) {
        UserScore score1 = new UserScore();
        score1.setUserId(userId);
        score1.setScoreType(scoreType);
        if (scoreType==0) {
            score1.setPostId(postId);
        }
        else if(scoreType==1) {
            score1.setDocId(docId);
        }
        score1.setScoreValue(ScoreEnum.CLICK.getCode());

        if(scoreMapper.addUserScore(score1)>0){
            log.info("用户ID = "+userId+"于 "+new Date()+" 点击了 postID = "+score1.getPostId()+",docID = "+score1.getDocId());
        }else {
            log.error("用户行为检测失败");
        }

        return score1;
    }

    @Override
    @Transactional
    public int clickAgain(int scoreId) {
        int updateNum = 0;

        try {
            UserScore score1 = scoreMapper.getUserScoreById(scoreId);
            score1.setScoreValue(score1.getScoreValue()+ScoreEnum.CLICK.getCode());
            updateNum = scoreMapper.updateUserScore(score1);
        }catch (Exception e){
            log.error("scoreId ="+scoreId+"的评分更新失败或未找到该评分项!");
        }finally {
            return updateNum;
        }
    }

    @Override
    @Transactional
    public int addComment(int scoreId) {
        int updateNum = 0;

        try {
            UserScore score1 = scoreMapper.getUserScoreById(scoreId);
            score1.setScoreValue(score1.getScoreValue()+ScoreEnum.REPLY.getCode());
            updateNum = scoreMapper.updateUserScore(score1);
        }catch (Exception e){
            log.error("scoreId ="+scoreId+"的评分更新失败或未找到该评分项!");
        }finally {
            return updateNum;
        }
    }

    @Override
    @Transactional
    public int addStar(int scoreId) {
        int updateNum = 0;

        try {
            UserScore score1 = scoreMapper.getUserScoreById(scoreId);
            score1.setScoreValue(score1.getScoreValue()+ScoreEnum.STAR.getCode());
            updateNum = scoreMapper.updateUserScore(score1);
        }catch (Exception e){
            log.error("scoreId ="+scoreId+"的评分更新失败或未找到该评分项!");
        }finally {
            return updateNum;
        }
    }

    @Override
    public int update(UserScore userScore) {
        int updateNum = 0;
        try {
            updateNum = scoreMapper.updateUserScore(userScore);
        }catch (Exception e){
            log.error("scoreId ="+userScore+"的评分项更新失败或未找到该评分项!");
        }finally {
            return updateNum;
        }
    }

    @Override
    public UserScore findOneByUserIdAndPostId(int userId, int postId) {
        UserScore score = null;
        try {
            score = scoreMapper.getUserScoreByUserIdAndPostId(userId, postId);
        }catch (Exception e){
            log.error("未找到 userID= "+userId+"postID = "+postId+"的相关评分记录");
        }finally {
            return score;
        }
    }

    @Override
    public int deleteById(int id) {
        return scoreMapper.deleteUserScoreById(id);
    }
}
