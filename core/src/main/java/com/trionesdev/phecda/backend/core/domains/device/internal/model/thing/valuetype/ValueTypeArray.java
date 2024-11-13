package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeArray extends ValueType{
    private ValueTypeEnum subValueType;
    private List<ValueType> valueSpecs;
    private Integer size;
}
