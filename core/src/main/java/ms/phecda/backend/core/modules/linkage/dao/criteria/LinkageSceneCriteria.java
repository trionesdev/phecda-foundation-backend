package ms.phecda.backend.core.modules.linkage.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
