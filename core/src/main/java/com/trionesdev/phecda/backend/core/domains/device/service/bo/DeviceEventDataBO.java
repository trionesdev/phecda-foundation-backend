package com.trionesdev.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.ThingModelEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceEventDataBO extends ThingModelEvent {
    private String latestTime;
    private Object value;
}
