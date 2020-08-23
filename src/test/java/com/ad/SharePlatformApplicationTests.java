package com.ad;

import com.ad.pojo.PostInfo;
import com.ad.pojo.TagInfo;
import com.ad.pojo.TagLink;
import com.ad.service.Impl.PostServiceImpl;
import com.ad.service.Impl.TagLinkServiceImpl;
import com.ad.service.Impl.TagServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@SpringBootTest
class SharePlatformApplicationTests {

    @Test
    void contextLoads() {

//        if(!result.getString("code").equals("success")){
//            //请求失败就再请求一次
//            result = restTemplate.postForObject(url,entity,JSONObject.class);
//        }
    }

}
