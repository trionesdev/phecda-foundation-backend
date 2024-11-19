package com.trionesdev.phecda.model.device;

import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class PhecdaMessage {
    private String version;
    private String type;
    private String id;
    private String deviceName;
    private String productKey;
    private Long ts;
    private Map<String, Reading> readings = new HashMap<>();
    private Map<String, Object> tags;

    @Data
    public static class Reading {
        private Long ts;
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
