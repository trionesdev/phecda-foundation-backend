package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.query;

import lombok.Data;

@Data
public class DeviceQuery {
    private String productId;
    private String productKey;
    private String nodeId;
    private String gatewayId;
    private String name;
    private String remarkName;
}
