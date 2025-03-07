package com.trionesdev.phecda.foundation.core.domains.device.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NodeType {
    DIRECT("直连设备"),
    GATEWAY("网关设备"),
    GATEWAY_SUB("网关子设备");

    @Getter
    private final String label;
}
