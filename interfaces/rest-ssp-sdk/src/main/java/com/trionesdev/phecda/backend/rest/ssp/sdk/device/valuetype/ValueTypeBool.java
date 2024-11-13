package com.trionesdev.phecda.backend.rest.ssp.sdk.device.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType {
    private Boolean value;
    private String name;
}
