package com.ad.service.Impl;

import com.ad.mapper.AdMapper;
import com.ad.pojo.AdInfo;
import com.ad.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/31 9:35
 *
 * @version 1.0
 */
@Service
@Slf4j
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdMapper adMapper;

    @Override
    @Transactional
    public AdInfo addAd(int userId, String title, String pic, String contact) {
        AdInfo adInfo  = new AdInfo();
        adInfo.setAdContact(contact);
        adInfo.setAdPic(pic);
        adInfo.setAdTitle(title);
        adInfo.setUserId(userId);

        try {
            adMapper.addAd(adInfo);
        }catch (Exception e){
            log.error("用户ID = "+userId+"添加广告发生错误！");
        }finally {
            return adInfo;
        }

    }

    @Override
    public AdInfo findOneById(int id) {
        AdInfo adInfo = null;
        try {
            adInfo = adMapper.getAdById(id);
        }catch (Exception e){
            log.error("查询id="+id+"的广告失败!");
        }finally {
            return adInfo;
        }
    }

    @Override
    public List<AdInfo> findByTitle(String title) {
        List<AdInfo> adInfos = null;
        try {
            adInfos = adMapper.getAdByTitle(title);
        }catch (Exception e){
            log.error("查询内容为'"+title+"'的广告失败!");
        }finally {
            return adInfos;
        }
    }

    @Override
    public List<AdInfo> findByUserId(int userId) {
        List<AdInfo> adInfos = null;
        try {
            adInfos = adMapper.getAdByUserId(userId);
        }catch (Exception e){
            log.error("查询用户ID为'"+userId+"'的广告失败!");
        }finally {
            return adInfos;
        }
    }

    @Override
    public List<String> findContactByUserId(int userId) {
        List<String> contact = null;
        try {
            contact = adMapper.getContactByUserId(userId);
        }catch (Exception e){
            log.error("查询用户ID为'"+userId+"'的广告联系方式失败!");
        }finally {
            return contact;
        }
    }

    @Override
    public List<AdInfo> findByContact(String contact) {
        List<AdInfo> adInfos = null;
        try {
            adInfos = adMapper.getAdByContact(contact);
        }catch (Exception e){
            log.error("查询联系方式为'"+contact+"'的广告失败!");
        }finally {
            return adInfos;
        }
    }

    @Override
    public int update(AdInfo adInfo) {
        int updateNum = 0;
        try {
            updateNum = adMapper.updateAd(adInfo);
        }catch (Exception e){
            log.error("Id ="+adInfo.getAdId()+"内容为:"+adInfo.getAdTitle()+"的广告更新失败!");
        }finally {
            return updateNum;
        }
    }

    @Override
    public int deleteById(int id) {
        int deleteNum = 0;
        try {
            deleteNum = adMapper.deleteAdById(id);
        }catch (Exception e){
            log.error("Id = "+id+"的广告删除失败!");
        }finally {
            return deleteNum;
        }
    }
}
