package com.trionesdev.phecda.foundation.core.domains.device.dao.dvo;

import lombok.Data;

@Data
public class DeviceStatisticsDVO {
    private Integer count;
    private Integer enabledCount;
    private Integer disabledCount;
}
