package ms.triones.backend.core.provider.ssp.device.pdo;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.Device.Protocol;

import java.util.List;

@Data
public class DevicePDO {
    private String id;
    private String productId;
    private String name;
    private String remarkName;
    private String nodeId;
    private String gatewayId;
    private List<Protocol> protocols;
    private Boolean activated;
    private Boolean enabled;
}
