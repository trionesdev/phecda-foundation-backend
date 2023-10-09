package ms.phecda.backend.core.domains.linkage.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LinkageSceneCriteria {
    private String name;
    private Boolean enabled;

    private Integer pageNum;
    private Integer pageSize;
}
