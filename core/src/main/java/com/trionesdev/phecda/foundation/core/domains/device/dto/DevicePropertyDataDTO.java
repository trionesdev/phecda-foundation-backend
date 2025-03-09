package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DevicePropertyDataDTO {
    private Instant ts;
    private String deviceName;
    private String name;
    private String identifier;
    private Object value;
}
