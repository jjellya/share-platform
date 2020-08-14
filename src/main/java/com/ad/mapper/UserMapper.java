package com.ad.mapper;

import com.ad.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author XJH
 */
@Mapper
public interface UserMapper {

    /**
     * 通过OpenId查找user
     * @Parm openId
     * @return
     * */
    public UserInfo getUserByOpenId(String openId);

    /**
     * 通过ID查找user
     * @param Id
     * @return
     */
    public UserInfo getUserById(Integer Id);

    /**
     * 通过名字查找user
     * @param name
     * @param name
     * @return
     */
    public List<UserInfo> getUserByName(String name);

    /**
     * 添加user
     * @param user
     * @return
     */
    public int addUser(UserInfo user);

    /**
     * 更新user
     * @param user
     * @return
     */
    public  int updateUser(UserInfo user);

    /**
     * 删除指定id的user列
     * @param Id
     * @return
     */
    public  int deleteUserById(Integer Id);


}
