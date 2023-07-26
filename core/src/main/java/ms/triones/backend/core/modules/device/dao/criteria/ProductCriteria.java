package ms.triones.backend.core.modules.device.dao.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ms.triones.backend.core.modules.device.dao.entity.Product;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCriteria {
    private String name;
    private Product.NodeType nodeType;
}
