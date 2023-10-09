package ms.phecda.backend.core.domains.device.dao.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeTypeEnum {
    DIRECT("直连设备"),
    GATEWAY("网关设备"),
    GATEWAY_SUB("网关子设备");

    @Getter
    private final String label;
}
