package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.Product;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ProductCreateRO {
    @NotBlank
    private String name;
    @Pattern(regexp = "^(?:[a-zA-Z].*)?$", message = "ProductKey必须是英文字母开头")
    private String key;
    @NotNull
    private NodeTypeEnum nodeType;
    @NotNull
    private AccessChannelEnum accessChannel;
    private Product.Type type;
    private String driverName;
}
