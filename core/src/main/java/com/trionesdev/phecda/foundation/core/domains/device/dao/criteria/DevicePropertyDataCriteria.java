package com.trionesdev.phecda.foundation.core.domains.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class DevicePropertyDataCriteria {
    private String deviceName;
    private Instant startTime;
    private Instant endTime;
    private String identifier;
    private String nodeId;
}
