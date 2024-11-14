package com.trionesdev.phecda.foundation.rest.tenant.domains.devicedata.controller.query;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class DeviceDataQuery {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private String deviceName;
    @NotNull
    private String field;

}
