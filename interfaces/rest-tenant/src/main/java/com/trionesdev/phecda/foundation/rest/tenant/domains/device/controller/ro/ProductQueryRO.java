package com.trionesdev.phecda.foundation.rest.tenant.domains.device.controller.ro;

import lombok.Data;
import com.trionesdev.phecda.foundation.core.domains.device.internal.enums.NodeType;

@Data
public class ProductQueryRO {
    private String name;
    private NodeType nodeType;
}
