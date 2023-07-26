package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductCreateRO {
    @NotBlank
    private String name;
    @NotNull
    private Product.NodeType nodeType;
    private String driverName;
}
