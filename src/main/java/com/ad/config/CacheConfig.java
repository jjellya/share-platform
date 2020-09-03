package com.ad.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight(最大权重)不可以同时使用，
                .maximumSize(1000)
                //最后一次写入或者访问后过久过期
                .expireAfterAccess(10, TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        //是否允许值为空
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }
}
