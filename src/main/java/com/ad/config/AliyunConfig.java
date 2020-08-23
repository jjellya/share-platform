package com.ad.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Create By  @林俊杰
 * 2020/8/23 20:45
 *
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    @Value("${aliyun.endpoint}")
    private String aliyunEndpoint;

    @Value("${aliyun.accessKeyId}")
    private String aliyunAccessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private String aliyunAccessKeySecret;

    @Value("${aliyun.bucket}")
    private String aliyunBucket;
}
