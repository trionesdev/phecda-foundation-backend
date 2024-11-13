package com.trionesdev.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelCommand;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceServiceDataBO extends ThingModelCommand {
    private Instant latestTime;
    private Object value;
}
