package ms.phecda.infrastructure.conf.cache;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "triones.cache")
public class TrionesCacheProperties {
    private Map<String, CacheItem> cacheNames;

    @Data
    @RequiredArgsConstructor
    public static class CacheItem {
        private Long timeToLeave;
        private ValueSerializerEnum valueSerializer;
    }

    public enum ValueSerializerEnum {
        STRING,
        JSON
    }
}
