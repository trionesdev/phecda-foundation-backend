package com.trionesdev.phecda.backend.rest.backend.domains.alarm.controller.ro;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AlarmLevelChangeEnabledRO {
    @NotNull
    private Boolean enabled;
}
