package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import lombok.Data;

@Data
public class DeviceQueryRO {
    private String productId;
    private String productKey;
    private String nodeId;
    private String gatewayId;
    private String name;
    private String remarkName;
}
