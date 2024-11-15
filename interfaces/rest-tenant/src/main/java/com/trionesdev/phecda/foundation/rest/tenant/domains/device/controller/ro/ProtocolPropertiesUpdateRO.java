package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO.ProtocolProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProtocolPropertiesUpdateRO {
    private List<ProtocolProperty> protocolProperties = new ArrayList<>();
}
