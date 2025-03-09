package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class DevicePropertyDataQueryRO {
    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
    @NotNull
    private String deviceName;
    @NotNull
    private String identifier;

}
