package com.ad.mapper;

import com.ad.enums.SQLEnum;
import com.ad.enums.ScoreEnum;
import com.ad.pojo.UserScore;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/8/21 19:25
 *
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserScoreMapperTest {

    @Autowired
    private UserScoreMapper scoreMapper;

    @Test
    void getUserScoreById() {
        UserScore result = scoreMapper.getUserScoreById(1);
        Assert.assertNotNull(result);
    }

    @Test
    void getUserScoreByUserId() {
        List<UserScore> result = scoreMapper.getUserScoreByUserId(1);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void getUserScoreByPostId() {
        List<UserScore> result = scoreMapper.getUserScoreByPostId(1);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void getUserScoreByDocId() {
        List<UserScore> result = scoreMapper.getUserScoreByDocId(1);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void getExistScoreByUserId() {
        List<UserScore> result = scoreMapper.getExistScoreByUserId(3);
        System.out.println(result.toString());
        Assert.assertNotEquals(0,result);
    }

    @Test
    void addUserScore() {
        UserScore userScore = new UserScore();
        userScore.setScoreType(SQLEnum.POST_TYPE.getCode());
        userScore.setPostId(1);
        userScore.setUserId(3);
        userScore.setScoreValue(ScoreEnum.REPLY.getCode()+ScoreEnum.CLICK.getCode());

        int result = scoreMapper.addUserScore(userScore);
        Assert.assertNotEquals(0,result);
    }

    @Test
    void updateUserScore() {
        UserScore userScore = new UserScore();
        userScore.setScoreId(2);
        userScore.setScoreType(SQLEnum.DOC_TYPE.getCode());
        userScore.setDocId(1);
        userScore.setUserId(3);
        userScore.setScoreValue(ScoreEnum.CLICK.getCode());

        int result = scoreMapper.updateUserScore(userScore);
        Assert.assertNotEquals(0,result);

    }

    @Test
    void deleteUserScoreById() {
        int result = scoreMapper.deleteUserScoreById(4);
        Assert.assertNotEquals(0,result);
    }
}