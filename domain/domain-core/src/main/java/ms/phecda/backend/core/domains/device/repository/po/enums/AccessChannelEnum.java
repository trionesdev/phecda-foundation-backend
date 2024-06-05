package ms.phecda.backend.core.domains.device.repository.po.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessChannelEnum {
    MQTT("MQTT"),
    GATEWAY("平台网关");

    private final String label;
}
