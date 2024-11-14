package com.trionesdev.phecda.foundation.rest.ssp.sdk.device.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType {
    private Boolean value;
    private String name;
}
