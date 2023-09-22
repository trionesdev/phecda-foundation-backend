package ms.phecda.backend.core.provider.middleware.cache;

import java.util.concurrent.TimeUnit;

public interface CacheProvider<K, V> {
    void setValue(K key, V value, long timeout, TimeUnit unit);

    V getValue(K key);
}
