package ms.phecda.backend.rest.backend.modules.edge.controller.query;

import lombok.Data;

@Data
public class NodeQuery {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
