package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductProtocolPropertiesUpdateRO {
    private List<Product.ProtocolProperty> protocolProperties = new ArrayList<>();
}
