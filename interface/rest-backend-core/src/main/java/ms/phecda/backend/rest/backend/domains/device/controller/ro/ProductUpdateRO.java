package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;


@Data
public class ProductUpdateRO {
    @NotBlank
    private String name;
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
    private String key;
    @NotNull
    private NodeTypeEnum nodeType;
    private Product.Type type;
    private String driverName;
}
