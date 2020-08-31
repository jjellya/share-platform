package com.ad.mapper;

import com.ad.pojo.DocInfo;
import com.ad.pojo.PostInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/30 20:47
 *
 * @version 1.0
 */
@Mapper
public interface SearchMapper {

    /**
     * 通过关键字查找post
     * @param keyword
     * @param offset
     * @param size
     * @return
     */
    public List<PostInfo> queryPostInfo(String keyword,int offset,int size);


    /**
     * 通过关键字查找post
     * @param keyword
     * @param offset
     * @param size
     * @return
     */
    public List<DocInfo> queryDocInfo(String keyword,int offset,int size);

}
