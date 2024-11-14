package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.dao.po.ProductPO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductProtocolPropertiesUpdateRO {
    private List<ProductPO.ProtocolProperty> protocolProperties = new ArrayList<>();
}
