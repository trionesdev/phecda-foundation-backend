package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeBool extends ValueType{
    private Boolean value;
    private String name;
}
