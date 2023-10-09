package ms.phecda.backend.core.domains.edge.dao.criteria;

import lombok.Data;

@Data
public class NodeCriteria {
    private String name;

    private Integer pageNum;
    private Integer pageSize;
}
