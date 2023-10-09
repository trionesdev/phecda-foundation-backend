package ms.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.Data;

@Data
public class DeviceQuery {
    private String productId;
    private String nodeId;
    private String gatewayId;
    private String name;
    private String remarkName;
}
