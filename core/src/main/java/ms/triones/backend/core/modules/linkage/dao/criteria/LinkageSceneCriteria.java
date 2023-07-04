package ms.triones.backend.core.modules.linkage.dao.criteria;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LinkageSceneCriteria {
    private Boolean enabled;
}
