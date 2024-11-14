package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 设备物模型信息，编译驱动加载使用
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductThingModelProfileDTO {
    private String productKey;
    private String manufacturer;
    private String description;
    private List<String> labels;
    private List<DeviceProperty> deviceProperties;
    private List<DeviceCommand> deviceCommands;
    private List<DeviceEvent> deviceEvents;


    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceProperty {
        private String identifier;
        private String name;
        private String description;
        private ValueProperties properties;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceCommand {
        private String identifier;
        private String name;
        private String description;
        private String callType;
        private List<ValueItem> inputData;
        private List<ValueItem> outputData;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeviceEvent {
        private String identifier;
        private String name;
        private String description;
        private String type;
        private List<ValueItem> outputData;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueItem {
        private String identifier;
        private String name;
        private ValueProperties properties;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueProperties {
        private String valueType;
        private String readWrite;
    }


}
