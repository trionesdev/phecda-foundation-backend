package ms.phecda.backend.core.domains.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.phecda.backend.core.domains.device.internal.model.thing.ThingModelProperty;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class DevicePropertyDataBO extends ThingModelProperty {
    private Instant latestTime;
    private Object value;
}
