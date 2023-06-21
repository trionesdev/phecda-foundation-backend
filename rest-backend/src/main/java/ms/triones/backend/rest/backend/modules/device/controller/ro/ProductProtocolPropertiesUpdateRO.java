package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductProtocolPropertiesUpdateRO {
    private List<Product.ProtocolProperty> protocolProperties = new ArrayList<>();
}
