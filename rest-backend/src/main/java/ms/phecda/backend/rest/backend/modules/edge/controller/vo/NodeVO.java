package ms.phecda.backend.rest.backend.modules.edge.controller.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class NodeVO {
    private String id;
    private String name;
    private String identifier;
    private String remark;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
