package com.ad.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create By  @林俊杰
 * 2020/8/28 17:37
 *
 * @version 1.0
 */
//1.找对象
//2.放到spring中待用
@Configuration
public class ElasticSearchClientConfig {

    //spring <bean id = restHighLevelClient(名字),class = RestHighLevelClient(类名)>
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("120.79.175.205", 9200, "http")));
        return client;
    }
}
