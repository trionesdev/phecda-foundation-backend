package ms.phecda.backend.core.domains.device.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.repository.po.enums.NodeTypeEnum;

import java.util.Collection;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCriteria {
    private String productId;
    private String nodeId;
    private String gatewayId;
    private String productKey;
    private NodeTypeEnum nodeType;
    private String name;
    private String remarkName;

    private Collection<String> names;
    private Collection<String> ids;
}
