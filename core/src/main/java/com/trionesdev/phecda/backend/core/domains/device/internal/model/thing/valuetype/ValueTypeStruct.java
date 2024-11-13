package com.trionesdev.phecda.backend.core.domains.device.internal.model.thing.valuetype;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValueTypeStruct extends ValueType {
    private String identifier;
    private String name;
    private ValueTypeEnum childValueType;
    private ValueType valueSpec;
    private List<ValueType> valueSpecs;
}
