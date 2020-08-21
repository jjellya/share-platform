package com.ad.service;

import com.ad.dto.PostDTO;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/21 20:06
 *
 * @version 1.0
 */
public interface RecommendService {
    List<PostDTO> findListOrderByRecommend(int offset, int size, int userId);
}
