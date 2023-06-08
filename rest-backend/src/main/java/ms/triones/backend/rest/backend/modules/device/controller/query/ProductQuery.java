package ms.triones.backend.rest.backend.modules.device.controller.query;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.Product;

@Data
public class ProductQuery {
    private Product.NodeType nodeType;
}
