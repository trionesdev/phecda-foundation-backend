package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.repository.po.DevicePO;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceProtocolUpdateRO {
    private List<DevicePO.Protocol> protocols = new ArrayList<>();
}
