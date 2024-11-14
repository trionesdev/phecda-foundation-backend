package com.trionesdev.phecda.foundation.core.domains.device.internal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessChannel {
    DRIVER("驱动"),
    GATEWAY("平台网关");

    private final String label;
}
