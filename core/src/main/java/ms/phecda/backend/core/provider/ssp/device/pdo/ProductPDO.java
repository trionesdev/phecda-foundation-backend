package ms.phecda.backend.core.provider.ssp.device.pdo;

import lombok.Data;
import ms.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;

@Data
public class ProductPDO {
    private String id;
    private String name;
    private String key;
    private NodeType nodeType;
    private AccessChannel accessChannel;
    private String thingModelVersion;
}
