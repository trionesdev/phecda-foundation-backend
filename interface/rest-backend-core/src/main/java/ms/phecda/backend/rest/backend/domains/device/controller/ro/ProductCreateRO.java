package ms.phecda.backend.rest.backend.domains.device.controller.ro;

import lombok.Data;
import ms.phecda.backend.core.domains.device.dao.entity.enums.AccessChannelEnum;
import ms.phecda.backend.core.domains.device.dao.entity.enums.NodeTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductCreateRO {
    @NotBlank
    private String name;
    private String key;
    @NotNull
    private NodeTypeEnum nodeType;
    @NotNull
    private AccessChannelEnum accessChannel;
    private String driverName;
}
