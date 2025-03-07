package com.trionesdev.phecda.foundation.core.domains.device.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    SENSOR("传感器"),
    CAMERA("摄像头"),
    GATEWAY("网关"),
    ;
    private final String label;
}
