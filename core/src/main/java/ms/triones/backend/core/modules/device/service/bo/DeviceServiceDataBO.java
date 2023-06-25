package ms.triones.backend.core.modules.device.service.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ms.triones.backend.core.modules.device.thing.model.ThingModelService;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceServiceDataBO extends ThingModelService {
    private Instant latestTime;
    private Object value;
}
