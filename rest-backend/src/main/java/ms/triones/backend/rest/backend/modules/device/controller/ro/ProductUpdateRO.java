package ms.triones.backend.rest.backend.modules.device.controller.ro;

import lombok.Data;
import ms.triones.backend.core.modules.device.dao.entity.enums.NodeTypeEnum;

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
