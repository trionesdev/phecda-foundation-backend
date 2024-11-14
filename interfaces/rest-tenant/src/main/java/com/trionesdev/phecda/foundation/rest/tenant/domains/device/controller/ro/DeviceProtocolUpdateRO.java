package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.DevicePO;

import java.util.ArrayList;
import java.util.List;

@Data
public class DeviceProtocolUpdateRO {
    private List<DevicePO.Protocol> protocols = new ArrayList<>();
}
