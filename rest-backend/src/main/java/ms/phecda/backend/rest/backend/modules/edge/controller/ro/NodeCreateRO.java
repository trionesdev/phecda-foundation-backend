package ms.phecda.backend.rest.backend.modules.edge.controller.ro;

import lombok.Data;

@Data
public class NodeCreateRO {
    private String name;
    private String identifier;
    private String remark;
}
