package com.trionesdev.phecda.backend.core.provider.ssp.edge.pdo;

import lombok.Data;

import java.time.Instant;

@Data
public class NodePDO {
    private String id;
    private String name;
    private String identifier;
    private String remark;
    private Boolean def;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
