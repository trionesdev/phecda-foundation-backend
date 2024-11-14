package com.trionesdev.phecda.foundation.core.domains.device.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceExtDTO extends DevicePO {
    private ProductExtDTO product;
}
