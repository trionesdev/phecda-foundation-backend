package ms.phecda.backend.core.modules.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.phecda.backend.core.modules.device.thing.model.ThingModelEvent;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceEventDataBO extends ThingModelEvent {
    private String latestTime;
    private Object value;
}
