package ms.phecda.backend.rest.backend.modules.device.controller.query;

import lombok.Data;
import ms.phecda.backend.core.modules.device.dao.entity.enums.NodeTypeEnum;

@Data
public class ProductQuery {
    private String name;
    private NodeTypeEnum nodeType;

    private Integer pageNum;
    private Integer pageSize;
}
