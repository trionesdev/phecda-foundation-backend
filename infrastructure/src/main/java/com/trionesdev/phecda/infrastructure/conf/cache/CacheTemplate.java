package com.trionesdev.phecda.infrastructure.conf.cache;

import java.util.concurrent.TimeUnit;

public interface CacheTemplate<K, V> {
    void setValue(K key, V value, long timeout, TimeUnit unit);

    V getValue(K key);

    void delete(K key);
}
