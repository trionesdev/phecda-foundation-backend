package ms.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.Data;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;

@Data
public class ProductQuery {
    private String name;
    private NodeType nodeType;

    private Integer pageNum;
    private Integer pageSize;
}
