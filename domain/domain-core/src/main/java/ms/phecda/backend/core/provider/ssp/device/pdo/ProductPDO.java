package ms.phecda.backend.core.provider.ssp.device.pdo;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;

@Data
public class ProductPDO {
    private String id;
    private String name;
    private String key;
    private NodeTypeEnum nodeType;
    private AccessChannelEnum accessChannel;
    private String thingModelVersion;
}
