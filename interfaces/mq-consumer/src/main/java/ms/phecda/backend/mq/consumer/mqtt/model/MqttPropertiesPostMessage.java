package ms.phecda.backend.mq.consumer.mqtt.model;

import cn.hutool.core.map.MapUtil;
import lombok.Data;
import com.trionesdev.phecda.backend.core.internal.disruptor.propertiespost.PropertiesPostMessage;

import java.util.HashMap;
import java.util.Map;

@Data
public class MqttPropertiesPostMessage {
    private String version;
    private String type;
    private String id;
    private String deviceName;
    private String productKey;
    private String identifier;
    private Long ts;
    private Map<String, Reading> readings = new HashMap<>();
    private Map<String, Object> tags;

    @Data
    public static class Reading {
        private Long ts;
        private String identifier;
        private String valueType;
        private String utils;
        private byte[] binaryValue;
        private String mediaType;
        private Object objectValue;
        private String value;
    }

    public  PropertiesPostMessage toProcessMessage() {
        PropertiesPostMessage processMessage = new PropertiesPostMessage();
        processMessage.setId(this.getId());
        processMessage.setDeviceName(this.getDeviceName());
        processMessage.setProductKey(this.getProductKey());
        processMessage.setIdentifier(this.getIdentifier());
        processMessage.setTs(this.getTs());
        processMessage.setTags(this.getTags());
        if (MapUtil.isNotEmpty(this.getReadings())){
            this.getReadings().forEach((key, value) -> {
                PropertiesPostMessage.Reading reading = PropertiesPostMessage.Reading.builder()
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
