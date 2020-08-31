package com.ad.mapper;

import com.ad.pojo.AdInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/31 8:57
 *
 * @version 1.0
 */
@Mapper
public interface AdMapper {

    /**
     * 通过ID查找advertisement
     * @param id
     * @return
     */
    public AdInfo getAdById(Integer id);


    /**
     * 通过内容查找
     * @param title
     * @return
     */
    public List<AdInfo> getAdByTitle(String title);

    /**
     * 通过发布者ID查找联系方式
     * @param userId
     * @return
     */
    public List<String> getContactByUserId(Integer userId);

    /**
     * 通过发布者ID查找
     * @param userId
     * @return
     */
    public List<AdInfo> getAdByUserId(Integer userId);

    /**
     * 通过发布者联系方式查找
     * @param contact
     * @return
     */
    public List<AdInfo> getAdByContact(String contact);

    /**
     * 添加Ad
     * @param ad
     * @return
     */
    public int addAd(AdInfo ad);

    /**
     * 更新ad
     * @param ad
     * @return
     */
    public int updateAd(AdInfo ad);

    /**
     * 删除指定id的Ad
     * @param id
     * @return
     */
    public int deleteAdById(Integer id);

    /**
     * 获取size个的Ad
     * @param size
     * @return
     */
    public List<AdInfo> getListOrderByTime(Integer size);

}
