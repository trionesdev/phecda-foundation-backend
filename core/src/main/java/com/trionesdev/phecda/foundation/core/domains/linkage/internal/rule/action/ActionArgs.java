package com.trionesdev.phecda.foundation.core.domains.linkage.internal.rule.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ActionArgs {
    private String ruleName;
    private String productId;
    private String productKey;
    private String deviceId;
    private String deviceName;
    private Map<String, Reading> readings;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reading {
        private String identifier;
        private String label;
        private Object value;
    }
}
