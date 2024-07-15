package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.po.ProductPO;
import ms.phecda.backend.core.domains.device.internal.enums.AccessChannel;
import ms.phecda.backend.core.domains.device.internal.enums.NodeType;


@Data
public class ProductCreateRO {
    @NotBlank
    private String name;
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
    private String key;
    @NotNull
    private NodeType nodeType;
    @NotNull
    private AccessChannel accessChannel;
    private ProductPO.Type type;
    private String driverName;
}
