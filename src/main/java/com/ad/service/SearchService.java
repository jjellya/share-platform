package com.ad.service;

import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.ad.pojo.DocInfo;
import com.ad.pojo.ESDocument;
import com.ad.pojo.PostInfo;
import com.ad.pojo.UserInfo;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Create By  @林俊杰
 * 2020/8/30 16:36
 *
 * @version 1.0
 */
public interface SearchService {
    //以下为测试内容,尚未可以使用
    public List<Map<String,Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException;

    public Boolean addDataList(List<ESDocument> esDocuments) throws Exception;

    public Boolean addData(PostInfo postInfo, UserInfo userInfo, DocInfo docInfo);

    public Boolean isExist(String id);

    public Map<String,Object> getDataById(String id);

    public ESDocument findOneById(String id);

    public Boolean updateDataById(ESDocument esDocument,String id);

    public Boolean deleteDataById(String id);


    //以下方法可以使用
    List<PostDTO> searchPost(String keyword,int offset,int size);

    List<DocShowDTO> searchDoc(String keyword, int offset, int size);
}
