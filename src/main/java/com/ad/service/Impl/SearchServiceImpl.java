package com.ad.service.Impl;

import com.ad.converter.DocInfo2DocShowDTOConverter;
import com.ad.converter.PostInfo2PostDTOConverter;
import com.ad.dto.DocShowDTO;
import com.ad.dto.PostDTO;
import com.ad.mapper.PostMapper;
import com.ad.mapper.SearchMapper;
import com.ad.mapper.TagMapper;
import com.ad.mapper.UserMapper;
import com.ad.pojo.*;
import com.ad.service.SearchService;
import com.ad.utils.ESconst;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Create By  @林俊杰
 * 2020/8/30 16:40
 *
 * @version 1.0
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private TagLinkServiceImpl tagLinkService;

    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private SearchMapper searchMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private PostMapper postMapper;


    @Override
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws IOException {
        if (pageNo<=1){
            pageNo=1;
        }

        if (pageSize<0){
            pageSize=5;
        }

        SearchRequest searchRequest = new SearchRequest(ESconst.ES_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("userName", keyword);
        searchSourceBuilder.query(fuzzyQueryBuilder);

        //QueryBuilder queryBuilder= QueryBuilders.multiMatchQuery(keyword, "tag1", "tag2", "tag3","postTitle", "docName", "postContent", "userName");


        //searchSourceBuilder.query(queryBuilder);

        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));


        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        List<Map<String,Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            list.add(hit.getSourceAsMap());
        }
        return list;
    }

    @Override
    public Boolean addDataList(List<ESDocument> esDocuments ) throws Exception {

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i=0;i<esDocuments.size();i++){
            bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(esDocuments.get(i)), XContentType.JSON));

        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.hasFailures();
    }

    @Override
    @Transactional
    public Boolean addData(PostInfo postInfo, UserInfo userInfo, DocInfo docInfo) {
        //1.创建对象
        ESDocument esDocument = new ESDocument();

        if (userInfo!=null){
            esDocument.setUserId(userInfo.getUserId());
            esDocument.setUserName(userInfo.getUserName());
        }
        if (postInfo!=null){
            esDocument.setPostContent(postInfo.getPostContent());
            esDocument.setPostId(postInfo.getPostId());
            esDocument.setPostTitle(postInfo.getPostTitle());
            esDocument.setUpdateTime(postInfo.getUpdateTime());
        }

        if (docInfo != null){
            esDocument.setDocId(docInfo.getDocId());
            esDocument.setDocName(docInfo.getDocName());
        }


        try {
            List<TagLink> tagLinks = new ArrayList<>();
            if (docInfo != null) tagLinks.addAll(tagLinkService.findByDocId(docInfo.getDocId()));
            if (postInfo!=null) tagLinks.addAll(tagLinkService.findByPostId(postInfo.getPostId()));
            //放置标签
            int flag=0;
            if (tagLinks.size()>0)
            for (TagLink tagLink : tagLinks) {
                TagInfo tagInfo = tagService.findOneById(tagLink.getTagId());
                String tempTag  = tagInfo.getTagContent();
                if(tempTag!=null) {
                    if (flag < 1) {

                        esDocument.setTag1(tempTag);
                        flag++;
                    } else if (flag < 2) {
                        esDocument.setTag2(tempTag);
                        flag++;
                    } else if (flag < 3) {
                        esDocument.setTag3(tempTag);
                        flag++;
                    } else {
                        break;
                    }
                }
            }
        }catch (Exception e){
            log.error("标签获取失败");
        }


        //2.创建请求
        IndexRequest request = new IndexRequest(ESconst.ES_INDEX);

        request.timeout(TimeValue.timeValueSeconds(3));

        //4.将数据放入请求 json
        IndexRequest source = request.source(JSON.toJSONString(esDocument), XContentType.JSON);

        //5.客户端发送请求，获取相应结果
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request,RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("elasticSearch添加文档失败!");
            return false;
        }
    }


    @Override
    public Boolean isExist(String id) {
        GetRequest getRequest = new GetRequest(ESconst.ES_INDEX, id);
        //不获取返回的 _source的上下文
        getRequest.fetchSourceContext(new FetchSourceContext(false));

        getRequest.storedFields("_none_");

        boolean exists = false;
        try {
            exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("该ElasticSearch文档Id = "+id+"不存在");
        }finally {
            return exists;
        }
    }


    @Override
    public Map<String, Object> getDataById(String id) {
        GetRequest getRequest = new GetRequest(ESconst.ES_INDEX,id);

        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return getResponse.getSourceAsMap();
        } catch (IOException e) {
            log.error("该ElasticSearch文档Id = "+id+"获取失败");
            return null;
        }
    }

    @Override
    public ESDocument findOneById(String id) {
        GetRequest getRequest = new GetRequest(ESconst.ES_INDEX,id);

        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> sourceMap = getResponse.getSourceAsMap();
            ESDocument esDocument  = new ESDocument();
            esDocument.setUserName((String) sourceMap.get("userName"));
            esDocument.setPostTitle((String) sourceMap.get("postTitle"));
            esDocument.setPostContent((String) sourceMap.get("postContent"));
            esDocument.setTag1((String) sourceMap.get("tag1"));
            esDocument.setTag2((String) sourceMap.get("tag2"));
            esDocument.setTag3((String) sourceMap.get("tag3"));

            esDocument.setDocName((String) sourceMap.get("docName"));
            esDocument.setUpdateTime((Date) sourceMap.get("update"));

            esDocument.setUserId((Integer) sourceMap.get("userId"));
            esDocument.setPostId((Integer) sourceMap.get("postId"));
            esDocument.setDocId((Integer) sourceMap.get("docId"));

            return esDocument;

        } catch (IOException e) {
            log.error("该ElasticSearch文档Id = "+id+"获取失败");
            return null;
        }
    }

    @Override
    public Boolean updateDataById(ESDocument esDocument,String id) {
        UpdateRequest updateRequest = new UpdateRequest(ESconst.ES_INDEX,id);
        updateRequest.timeout("1s");

        updateRequest.doc(JSON.toJSONString(esDocument),XContentType.JSON);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("该ElasticSearch文档Id = "+id+"更新失败");
            return false;
        }
    }

    @Override
    public Boolean deleteDataById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(ESconst.ES_INDEX,id);
        deleteRequest.timeout("1s");

        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return true;
        } catch (IOException e) {
            log.error("该ElasticSearch文档Id = "+id+"删除失败");
            return false;
        }

    }

    @Override
    public List<PostDTO> searchPost(String keyword,int offset,int size) {
        List<PostDTO> postDTOList = new ArrayList<>();

        if(size <= 0){
            log.error("请求错误,size <= 0");
            return postDTOList;
        }

        List<PostInfo> postList = searchMapper.queryPostInfo(keyword,offset,size);

        UserInfo tempUser;
        List<TagInfo> tempTagList;

        if(postList!=null&&postList.size()>0) {
            for (PostInfo post : postList
            ) {
                tempUser = userMapper.getUserById(post.getUserId());
                tempTagList = tagMapper.getTagByPostId(post.getPostId());
                postDTOList.add(PostInfo2PostDTOConverter.convert(post,tempUser,tempTagList));
            }
        }
        return postDTOList;
    }

    @Override
    public List<DocShowDTO> searchDoc(String keyword, int offset, int size) {
        List<DocShowDTO> docShowDTOList = new ArrayList<>();

        if(size <= 0){
            log.error("请求错误,size <= 0");
            return docShowDTOList;
        }

        List<DocInfo> docList = searchMapper.queryDocInfo(keyword,offset,size);

        UserInfo tempUser;
        List<TagInfo> tempTagList;

        if(docList!=null&&docList.size()>0) {
            for (DocInfo doc : docList
            ) {
                tempUser = userMapper.getUserById(doc.getUserId());
                tempTagList = tagMapper.getTagByDocId(doc.getDocId());
                docShowDTOList.add(DocInfo2DocShowDTOConverter.convert(doc,tempUser,tempTagList));
            }
        }
        return docShowDTOList;
    }
}
