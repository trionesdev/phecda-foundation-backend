package com.trionesdev.phecda.foundation.core.domains.edge.dao.criteria;

import lombok.Data;

@Data
public class NodeCriteria {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
