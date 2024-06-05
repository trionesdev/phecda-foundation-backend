package ms.phecda.backend.core.domains.device.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.repository.po.enums.NodeTypeEnum;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCriteria {
    private String name;
    private NodeTypeEnum nodeType;
}
