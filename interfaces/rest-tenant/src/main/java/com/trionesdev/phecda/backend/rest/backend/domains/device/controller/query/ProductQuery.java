package com.trionesdev.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.Data;
import com.trionesdev.phecda.backend.core.domains.device.internal.enums.NodeType;

@Data
public class ProductQuery {
    private String name;
    private NodeType nodeType;

    private Integer pageNum;
    private Integer pageSize;
}
