package com.ad.service.Impl;

import com.ad.mapper.UserMapper;
import com.ad.pojo.UserInfo;
import com.ad.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/14 22:32
 *
 * @version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo addUser(String openid, String skey, String avatarUrl, Integer gender, String nickName) {
        UserInfo user = new UserInfo();

        user.setOpenId(openid);
        user.setSessionKey(skey);
        user.setAvatarUrl(avatarUrl);
        user.setUserGender(gender);
        user.setUserName(nickName);

        user.setUserMail(null);
        user.setUserGrade(1);
        user.setDocNum(0);
        user.setPostNum(0);
        user.setUserStar(0);
        userMapper.addUser(user);
        log.info("已成功创建新用户: Id="+user.getUserId()+",name = "+user.getUserName());

        return user;
    }

    @Override
    public UserInfo findOneByOpenId(String openId) {
        UserInfo userInfo = null;
        userInfo = userMapper.getUserByOpenId(openId);
        return userInfo;
    }

    @Override
    public UserInfo findOneById(Integer id) {
        UserInfo userInfo = null;
        userInfo = userMapper.getUserById(id);
        return userInfo;
    }

    @Override
    public List<UserInfo> findOneByName(String name) {
        List<UserInfo> userList = null;
        userList = userMapper.getUserByName(name);
        return userList;
    }

    @Override
    public void update(UserInfo user) {
        try {
            userMapper.updateUser(user);
        }catch (Exception e){
            log.error("用户: Id="+user.getUserId()+"更新失败!");
        }
    }
}
