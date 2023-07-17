package com.stock.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {


    @Bean(name = "caffeineCacheManager")
    @Override
    public CacheManager cacheManager() {
        Caffeine<Object, Object> moversCaffeine = Caffeine.newBuilder();
//        moversCaffeine.expireAfterWrite(2, TimeUnit.MINUTES);
        moversCaffeine.expireAfterWrite(6, TimeUnit.HOURS);
        CaffeineCacheManager moversCacheManager = new CaffeineCacheManager("getMovers");
        moversCacheManager.setCaffeine(moversCaffeine);

        Caffeine<Object, Object> overviewCompanyMoversCaffeine = Caffeine.newBuilder();
//        overviewCompanyMoversCaffeine.expireAfterWrite(1, TimeUnit.MINUTES);
        overviewCompanyMoversCaffeine.expireAfterWrite(1, TimeUnit.DAYS);
        CaffeineCacheManager overviewCompanyCacheManager = new CaffeineCacheManager("getOverviewCompany");
        overviewCompanyCacheManager.setCaffeine(overviewCompanyMoversCaffeine);

        Caffeine<Object, Object> fearIndexCaffeine = Caffeine.newBuilder();
//        overviewCompanyMoversCaffeine.expireAfterWrite(1, TimeUnit.MINUTES);
        fearIndexCaffeine.expireAfterWrite(1, TimeUnit.DAYS);
        CaffeineCacheManager fearIndexCacheManager = new CaffeineCacheManager("getFearGreedIndex");
        fearIndexCacheManager.setCaffeine(fearIndexCaffeine);

        Caffeine<Object, Object> stockPriceCaffeine = Caffeine.newBuilder();
        stockPriceCaffeine.expireAfterWrite(1, TimeUnit.MINUTES);
//        stockPriceCaffeine.expireAfterWrite(20, TimeUnit.SECONDS);
        CaffeineCacheManager stockPriceCacheManager = new CaffeineCacheManager("getStockPrice");
        stockPriceCacheManager.setCaffeine(stockPriceCaffeine);

        return new CompositeCacheManager(
                moversCacheManager,
                overviewCompanyCacheManager,
                stockPriceCacheManager,
                fearIndexCacheManager);
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}
