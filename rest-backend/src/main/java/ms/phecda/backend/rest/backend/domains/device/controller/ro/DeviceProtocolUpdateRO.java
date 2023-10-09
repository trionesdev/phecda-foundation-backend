package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.Device;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceProtocolUpdateRO {
    private List<Device.Protocol> protocols = new ArrayList<>();
}
