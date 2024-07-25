package ms.phecda.backend.core.internal.disruptor.propertiespost;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@SuperBuilder
@NoArgsConstructor
public class PropertiesPostMessage {
    private String type;
    private String version;
    private String id;
    private String productKey;
    private String productId;
    private String deviceName;
    private String deviceId;
    private String identifier;
    private Long ts;
    private Map<String, Reading> readings = new HashMap<>();
    private Map<String, Object> tags;

    @Data
    @SuperBuilder
    public static class Reading {
        private Long ts;
        private String identifier;
        private String valueType;
        private String utils;
        private byte[] binaryValue;
        private String mediaType;
        private Object objectValue;
        private String value;

        public Long getTs() {
            return Optional.ofNullable(ts).orElse(Instant.now().toEpochMilli());
        }

        public Object getReadingValue() {
            if (objectValue != null) {
                return objectValue;
            }
            if (value != null) {
                return value;
            }
            return null;
        }
    }

}
