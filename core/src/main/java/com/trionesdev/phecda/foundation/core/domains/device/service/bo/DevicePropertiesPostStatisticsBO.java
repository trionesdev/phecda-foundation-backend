package com.trionesdev.phecda.foundation.core.domains.device.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DevicePropertiesPostStatisticsBO {
    private Long total;
    private Long monthTotal;
    private Long dayTotal;
}
