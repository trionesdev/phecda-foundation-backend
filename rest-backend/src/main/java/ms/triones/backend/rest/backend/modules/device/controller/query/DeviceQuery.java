package ms.triones.backend.rest.backend.modules.device.controller.query;

import lombok.Data;

@Data
public class DeviceQuery {
    private String productId;
    private String gatewayId;
    private String name;
    private String remarkName;
}
