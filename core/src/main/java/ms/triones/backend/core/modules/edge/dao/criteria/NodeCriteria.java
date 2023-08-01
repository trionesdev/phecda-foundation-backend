package ms.triones.backend.core.modules.edge.dao.criteria;

import lombok.Data;

@Data
public class NodeCriteria {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
