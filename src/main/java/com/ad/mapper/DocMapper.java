package com.ad.mapper;

import com.ad.pojo.DocInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/23 16:18
 *
 * @version 1.0
 */
@Mapper
public interface DocMapper {
    /**
     * 通过ID查找doc
     * @param id
     * @return
     */
    public DocInfo getDocById(Integer id);


    /**
     * 通过文件名查找doc
     * @param name
     * @return
     */
    public List<DocInfo> getDocByName(String name);

    /**
     * 通过文件类型查找doc
     * @param docType
     * @return
     */
    public List<DocInfo> getDocByType(Integer docType);

    /**
     * 通过上传用户id查找doc
     * @param userId
     * @return
     */
    public List<DocInfo> getDocByUserId(Integer userId);


    /**
     * 添加docInfo
     * @param doc
     * @return
     */
    public int addDoc(DocInfo doc);

    /**
     * 更新docInfo
     * @param doc
     * @return
     */
    public int updateDoc(DocInfo doc);


    /**
     * 删除docInfo
     * @param id
     * @return
     */
    public int deleteDocById(Integer id);

}
