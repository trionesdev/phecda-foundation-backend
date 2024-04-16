package ms.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.phecda.backend.core.domains.device.internal.thing.model.ThingModelProperty;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class DevicePropertyDataBO extends ThingModelProperty {
    private Instant latestTime;
    private Object value;
}
