package com.trionesdev.phecda.foundation.mq.consumer.kafka.model;

import cn.hutool.core.map.MapUtil;
import com.trionesdev.phecda.model.device.PhecdaMessage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GatewayPropertiesPostMessage {
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

    public PhecdaMessage toProcessMessage(){
        PhecdaMessage processMessage = new PhecdaMessage();
        processMessage.setId(this.getId());
        processMessage.setDeviceName(this.getDeviceName());
        processMessage.setProductKey(this.getProductKey());
        processMessage.setTs(this.getTs());
        processMessage.setTags(this.getTags());
        if (MapUtil.isNotEmpty(this.getReadings())){
            this.getReadings().forEach((key, value) -> {
                PhecdaMessage.Reading reading = PhecdaMessage.Reading.builder()
                        .ts(value.getTs())
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
