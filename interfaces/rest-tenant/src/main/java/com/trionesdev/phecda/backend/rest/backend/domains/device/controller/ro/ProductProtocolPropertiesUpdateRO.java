package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.device.dao.po.ProductPO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductProtocolPropertiesUpdateRO {
    private List<ProductPO.ProtocolProperty> protocolProperties = new ArrayList<>();
}
