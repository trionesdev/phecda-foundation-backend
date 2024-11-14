package com.trionesdev.phecda.foundation.rest.tenant.domains.edge.controller.query;

import lombok.Data;

@Data
public class NodeQuery {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
