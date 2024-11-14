package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeviceServiceLogQuery {
    private String deviceName;
    private String serviceIdentifier;
    private Instant startTime;
    private Instant endTime;

    private Integer pageNum;
    private Integer pageSize;
}
