package com.trionesdev.phecda.backend.core.domains.device.dto;

import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.DevicePO.Protocol;

import java.util.List;

@Data
public class DeviceDTO {
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
