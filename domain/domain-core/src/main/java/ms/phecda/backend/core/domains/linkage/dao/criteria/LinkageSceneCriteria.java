package ms.phecda.backend.core.domains.linkage.dao.criteria;

import com.trionesdev.commons.core.page.PageCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LinkageSceneCriteria extends PageCriteria {
    private String name;
    private Boolean enabled;
}
