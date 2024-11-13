package com.trionesdev.phecda.backend.core.domains.device.dao.dvo;

import lombok.Data;

@Data
public class DeviceStatisticsDVO {
    private Integer count;
    private Integer enabledCount;
    private Integer disabledCount;
}
