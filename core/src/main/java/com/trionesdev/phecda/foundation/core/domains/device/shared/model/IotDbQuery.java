package com.trionesdev.phecda.foundation.core.domains.device.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IotDbQuery {
    private String productKey;
    private String deviceName;
    private List<String> columns;
    private long startTime;
    private long endTime;
}
