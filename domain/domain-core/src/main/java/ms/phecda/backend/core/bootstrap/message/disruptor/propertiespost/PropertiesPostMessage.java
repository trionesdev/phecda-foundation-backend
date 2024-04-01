package ms.phecda.backend.core.bootstrap.message.disruptor.propertiespost;

import cn.hutool.core.map.MapUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.messageaccess.mqtt.model.MqttPropertiesPostMessage;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@SuperBuilder
@NoArgsConstructor
public class PropertiesPostMessage {
    private String version;
    private String id;
    private String productKey;
    private String productId;
    private String deviceName;
    private String deviceId;
    private String sourceName;
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

    public static PropertiesPostMessage from(MqttPropertiesPostMessage message) {
        PropertiesPostMessage processMessage = new PropertiesPostMessage();
        processMessage.setId(message.getId());
        processMessage.setDeviceName(message.getDeviceName());
        processMessage.setProductKey(message.getProductKey());
        processMessage.setSourceName(message.getSourceName());
        processMessage.setTs(message.getTs());
        processMessage.setTags(message.getTags());
        if (MapUtil.isNotEmpty(message.getReadings())) {
            message.getReadings().forEach((key, value) -> {
                Reading reading = Reading.builder()
                        .ts(value.getTs())
                        .identifier(value.getIdentifier())
                        .valueType(value.getValueType())
                        .utils(value.getUtils())
                        .binaryValue(value.getBinaryValue())
                        .mediaType(value.getMediaType())
                        .objectValue(value.getObjectValue())
                        .value(value.getValue())
                        .build();
                processMessage.getReadings().put(key, reading);
            });
        }
        return processMessage;
    }

}
