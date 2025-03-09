package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PropertyDataDTO {
    private Instant timestamp;
    private String identifier;
    private Object value;
}
