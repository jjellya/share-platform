package com.ad.service;

import com.ad.pojo.DocInfo;

import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/8/23 19:38
 *
 * @version 1.0
 */
public interface DocService {

    DocInfo addDoc(String name,Integer type,Integer size,String path,Integer userId);

    DocInfo findOneById(Integer id);

    List<DocInfo> findByName(String name);

    List<DocInfo> findByType(Integer type);

    List<DocInfo> findByUserId(Integer userId);

    int update(DocInfo doc);

    int deleteById(Integer id);

}
