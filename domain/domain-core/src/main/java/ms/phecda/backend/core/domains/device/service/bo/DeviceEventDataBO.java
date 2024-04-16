package ms.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModelEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceEventDataBO extends ThingModelEvent {
    private String latestTime;
    private Object value;
}
