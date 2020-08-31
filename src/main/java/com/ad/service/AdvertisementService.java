package com.ad.service;

import com.ad.pojo.AdInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/31 9:30
 *
 * @version 1.0
 */
public interface AdvertisementService {
    AdInfo addAd(int userId,String title,String pic,String contact);

    AdInfo findOneById(int id);

    List<AdInfo> findByTitle(String title);

    List<AdInfo> findByUserId(int userId);

    List<String>  findContactByUserId(int userId);

    List<AdInfo> findByContact(String contact);

    int update(AdInfo adInfo);

    int deleteById(int id);
}
