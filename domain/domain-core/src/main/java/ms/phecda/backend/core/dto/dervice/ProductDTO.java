package ms.phecda.backend.core.dto.dervice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO.ProtocolProperty;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO.Type;
import ms.phecda.backend.core.domains.device.repository.po.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.repository.po.enums.NodeTypeEnum;
import ms.phecda.backend.core.domains.device.repository.po.enums.ProductStatusEnum;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String name;
    private String key;
    private NodeTypeEnum nodeType;
    private AccessChannelEnum accessChannel;
    private Type type;
    private List<ProtocolProperty> protocolProperties;

    private ProductStatusEnum status;
    private String driverName;
    private Driver driver;

    @Data
    public static class Driver {
        private String name;
        private String description;
    }
}
