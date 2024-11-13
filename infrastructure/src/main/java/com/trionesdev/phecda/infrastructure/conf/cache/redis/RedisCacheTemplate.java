package com.trionesdev.phecda.infrastructure.conf.cache.redis;

import com.trionesdev.phecda.infrastructure.conf.cache.CacheTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


public class RedisCacheTemplate<K, V> implements CacheTemplate<K, V> {

    private final RedisTemplate<K, V> redisTemplate;

    public RedisCacheTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setValue(K key, V value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public V getValue(K key) {
       return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(K key) {
        redisTemplate.delete(key);
    }
}
