package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.query;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class DevicePropertyDataQuery {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private String deviceName;
    @NotNull
    private String identifier;

}
