package com.trionesdev.phecda.foundation.rest.tenant.domains.linkage.controller.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkageSceneQuery {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
