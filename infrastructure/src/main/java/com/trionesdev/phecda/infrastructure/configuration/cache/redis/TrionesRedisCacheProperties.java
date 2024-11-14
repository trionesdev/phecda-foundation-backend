package com.trionesdev.phecda.infrastructure.configuration.cache.redis;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.cache.redis")
public class TrionesRedisCacheProperties {
    private Map<String, CacheItem> cacheNames;

    @Data
    @RequiredArgsConstructor
    public static class CacheItem {
        private Long timeToLeave;
        private ValueTypeEnum valueType;
    }

    public enum ValueTypeEnum {
        STRING,
        JSON
    }
}
