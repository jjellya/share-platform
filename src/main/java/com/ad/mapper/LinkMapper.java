package com.ad.mapper;

import com.ad.pojo.LinkInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/28 17:48
 *
 * @version 1.0
 */
@Mapper
public interface LinkMapper {

    /**
     * 通过ID查找Link
     * @param id
     * @return
     */
    public LinkInfo getLinkById(Integer id);

    /**
     * 通过userId查找tagLink
     * @param userId
     * @return
     */
    public List<LinkInfo> getLinkByUserId(Integer userId);

    /**
     * 通过docId查找tagLink
     * @param docId
     * @return
     */
    public List<LinkInfo> getLinkByDocId(Integer docId);

    /**
     * 通过docId和userId查找tagLink
     * @param userId
     * @param docId
     * @return
     */
    public LinkInfo getLinkByUserIdAndDocId(Integer userId,Integer docId);


    /**
     * 添加Link
     * @param linkInfo
     * @return
     */
    public int addLink(LinkInfo linkInfo);

    /**
     * 更新Link
     * @param linkInfo
     * @return
     */
    public int updateLink(LinkInfo linkInfo);

    /**
     * 删除指定id的Link列
     * @param id
     * @return
     */
    public int deleteLinkById(Integer id);

}
