package com.ad.mapper;

import com.ad.pojo.UserScore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/21 18:08
 *
 * @version 1.0
 */
@Mapper
public interface UserScoreMapper {

    /**
     * 通过ID查找userScore
     * @param id
     * @return
     */
    public UserScore getUserScoreById(Integer id);

    /**
     * 通过usrId查找userScore
     * @param userId
     * @return
     */
    public List<UserScore> getUserScoreByUserId(Integer userId);


    /**
     * 通过postId查找userScore
     * @param postId
     * @return
     */
    public List<UserScore> getUserScoreByPostId(Integer postId);


    /**
     * 通过docId查找userScore
     * @param docId
     * @return
     */
    public List<UserScore> getUserScoreByDocId(Integer docId);

    /**
     * 通过userId查找 低Score,小于2分为低分
     * @param userId
     * @return
     */
    public List<UserScore> getExistScoreByUserId(Integer userId);

    /**
     * 添加user
     * @param userScore
     * @return
     */
    public int addUserScore(UserScore userScore);

    /**
     * 更新userScore
     * @param userScore
     * @return
     */
    public int updateUserScore(UserScore userScore);

    /**
     * 删除指定id的userScore
     * @param id
     * @return
     */
    public  int deleteUserScoreById(Integer id);
}
