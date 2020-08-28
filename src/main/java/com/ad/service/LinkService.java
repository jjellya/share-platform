package com.ad.service;

import com.ad.pojo.DocInfo;
import com.ad.pojo.LinkInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 18:04
 *
 * @version 1.0
 */
public interface LinkService {

    //doc的starNum已增加请勿重复添加
    LinkInfo addStar(Integer userId,Integer docId);

    LinkInfo findOneById(Integer id);

    List<LinkInfo> findByDocId(Integer docId);

    List<LinkInfo> findByUserId(Integer userId);

    int update(LinkInfo linkInfo);

    int deleteById(Integer id);

    List<DocInfo> findDocListByUserId(Integer userId);

    //doc的starNum已减少请勿重复减少
    int cancelStar(Integer userId,Integer docId);
}
