package com.trionesdev.phecda.model.device.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeString extends ValueType{
    private Integer length;
}
