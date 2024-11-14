package com.trionesdev.phecda.foundation.core.domains.device.service.bo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class DeviceStatisticsBO {
    private Integer count;
    private Integer enabledCount;
    private Integer disabledCount;
}
