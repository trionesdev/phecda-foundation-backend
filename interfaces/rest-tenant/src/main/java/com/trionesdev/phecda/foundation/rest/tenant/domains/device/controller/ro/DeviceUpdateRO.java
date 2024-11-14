package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceUpdateRO {
    @NotBlank
    private String id;

    @NotBlank
    private String remarkName;
}
