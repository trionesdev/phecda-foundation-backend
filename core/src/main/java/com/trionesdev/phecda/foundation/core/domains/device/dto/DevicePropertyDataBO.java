package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.trionesdev.phecda.model.device.thing.ThingModelProperty;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class DevicePropertyDataBO extends ThingModelProperty {
    private Instant latestTime;
    private Object value;
}
