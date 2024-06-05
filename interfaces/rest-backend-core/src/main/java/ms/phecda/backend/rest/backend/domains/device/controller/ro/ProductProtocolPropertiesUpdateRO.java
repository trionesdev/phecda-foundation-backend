package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.repository.po.ProductPO;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductProtocolPropertiesUpdateRO {
    private List<ProductPO.ProtocolProperty> protocolProperties = new ArrayList<>();
}
