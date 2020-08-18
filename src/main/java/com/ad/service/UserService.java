package com.ad.service;

import com.ad.pojo.UserInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/14 22:27
 *
 * @version 1.0
 */
public interface UserService {

    UserInfo addUser(String openid,String sessionKey,String avatarUrl,Integer gender,String nickName);

    UserInfo findOneByOpenId(String openId);

    UserInfo findOneById(Integer id);

    List<UserInfo> findOneByName(String name);

    int update(UserInfo user);
}
