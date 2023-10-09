package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductUpdateRO {
    @NotBlank
    private String name;
    @NotNull
    private NodeTypeEnum nodeType;
    private String driverName;
}
