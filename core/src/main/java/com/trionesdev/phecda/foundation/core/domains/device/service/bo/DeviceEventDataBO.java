package com.trionesdev.phecda.foundation.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.trionesdev.phecda.model.device.thing.ThingModelEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceEventDataBO extends ThingModelEvent {
    private String latestTime;
    private Object value;
}
