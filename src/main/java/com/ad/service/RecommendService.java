package com.ad.service;

import com.ad.dto.PostDTO;
import com.ad.pojo.UserScore;
import com.sun.istack.Nullable;


import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/21 20:06
 *
 * @version 1.0
 */
public interface RecommendService {
    List<PostDTO> findListOrderByRecommend(int offset, int size, int userId,int grade);

    UserScore addScore(int userId, int scoreType, @Nullable int postId, @Nullable int docId);

    int clickAgain(int scoreId);

    int addComment(int scoreId);

    int addStar(int scoreId);

    int update(UserScore userScore);

    UserScore findOneByUserIdAndPostId(int userId,int postId);

    int deleteById(int id);


}
