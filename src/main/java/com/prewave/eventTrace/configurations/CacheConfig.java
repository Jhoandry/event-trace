package com.prewave.eventTrace.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${prewave.queryTerm.cacheName}")
    protected String queryTermCacheName;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager( queryTermCacheName);
    }
}
