package ms.phecda.backend.rest.backend.domains.device.controller.query;

import lombok.Data;
import ms.phecda.backend.core.domains.device.repository.po.enums.NodeTypeEnum;

@Data
public class ProductQuery {
    private String name;
    private NodeTypeEnum nodeType;

    private Integer pageNum;
    private Integer pageSize;
}
