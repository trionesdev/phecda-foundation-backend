package com.trionesdev.phecda.model.device;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PhecdaMessage {
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
}
