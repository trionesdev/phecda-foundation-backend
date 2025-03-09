package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DevicePropertyDataLastQueryRO {
    @NotNull
    private String deviceName;
    private String identifier;
}
