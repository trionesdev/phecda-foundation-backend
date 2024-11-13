package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeString extends ValueType{
    private Integer length;
}
