package com.trionesdev.phecda.model.device.thing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CallType {
    ASYNC("Async"),
    SYNC("Sync");

    private final String value;
}
